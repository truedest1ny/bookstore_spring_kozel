package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.data.entity.UserHash;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.UserRepository;
import com.kozel.bookstore.service.Hasher;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.UserChangePasswordDto;
import com.kozel.bookstore.service.dto.UserCreateDto;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserLoginDto;
import com.kozel.bookstore.service.dto.UserShowingDto;
import com.kozel.bookstore.service.dto.UserUpdateDto;
import com.kozel.bookstore.service.exception.AuthentificationException;
import com.kozel.bookstore.service.exception.InvalidPasswordException;
import com.kozel.bookstore.service.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DataMapper dataMapper;
    private final Hasher hasher;


    @Override
    public List<UserDto> getAll() {

        log.debug("Called getAll() method");

        return userRepository.findAll()
                .stream()
                .map(dataMapper::toDto)
                .toList();
    }

    @Override
    public List<UserShowingDto> getUsersDtoShort() {

        log.debug("Called getUsersDtoShort() method");

        return userRepository.findAll()
                .stream()
                .map(dataMapper::toShortedDto)
                .toList();
    }

    @Override
    public UserDto getById(Long id) {
            log.debug("Called getById() method");

            User user = userRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find user by id " + id)
            );
            return dataMapper.toDto(user);
    }

    @Override
    public UserDto getByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(
                () -> new ResourceNotFoundException("No user with such login (" + login +").")
        );
        return dataMapper.toDto(user);
    }

    @Override
    public UserDto create(UserCreateDto userCreateDto) {

        log.debug("Called create() method");

        User entity = new User();

        entity.setEmail(userCreateDto.getEmail());
        entity.setLogin(userCreateDto.getLogin());


        entity.setRole(User.Role.CUSTOMER);


        UserHash hash = new UserHash();

        String salt = hasher.generateSalt();
        String password = userCreateDto.getPassword();

        String hashedPassword = hasher.hashPassword(password, salt);

        hash.setSalt(salt);
        hash.setHashedPassword(hashedPassword);

        entity.setHash(hash);
        hash.setUser(entity);

        User user = userRepository.save(entity);

        return dataMapper.toDto(user);

    }

    @Override
    public UserDto update(UserUpdateDto dto) {

        log.debug("Called update() method");

        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User " + dto.getId() + " not found"));

        dataMapper.mapToEntity(dto, user);

        return dataMapper.toDto(userRepository.save(user));
    }

    @Override
    public void disable(Long id) {
        log.debug("Called disable() method");

        try {
            UserDto user = dataMapper.toDto(
                    userRepository.findById(id).orElseThrow(
                            () -> new RuntimeException("Cannot find user (id = " + id + ")." +
                                    " There is nothing to delete. ")
                    ));
            userRepository.delete(dataMapper.toEntity(user));

        } catch (DataAccessException e){
            throw new RuntimeException("Cannot delete user (id = " + id + ")", e);
        }
    }

    @Override
    public UserDto login(UserLoginDto userLoginDto) {
            log.debug("Called login() method");

            User user = userRepository.findByLogin(userLoginDto.getLogin()).orElseThrow(
                    () -> new AuthentificationException(
                            "No user with such login (" + userLoginDto.getLogin() +").")
            );

            String userSalt = user.getHash().getSalt();
            String inputtedPassword = userLoginDto.getPassword();
            String userHashedPassword = user.getHash().getHashedPassword();

            if(!hasher.hashPassword(inputtedPassword, userSalt)
                    .equals(userHashedPassword)) {
             throw new AuthentificationException("Incorrect password for user (" + user.getLogin() + ")");
            }

            return dataMapper.toDto(user);
    }

    public void changePassword(UserChangePasswordDto changePasswordDto){
        User user = userRepository.findById(changePasswordDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        "No user with such ID (" + changePasswordDto.getId() +").")
        );

        String currentHashedPassword = user.getHash().getHashedPassword();

        String formHashedCurrentPassword = hasher.hashPassword(
                changePasswordDto.getCurrentPassword(),
                user.getHash().getSalt());

        String formHashedNewPassword = hasher.hashPassword(
                changePasswordDto.getNewPassword(),
                user.getHash().getSalt());

        if (!changePasswordDto.getNewPassword().
                equals(changePasswordDto.getConfirmPassword())){
            throw new InvalidPasswordException("The entered passwords do not match!");

        } else if (!currentHashedPassword.equals(formHashedCurrentPassword)){
            throw new InvalidPasswordException("Typed wrong current password!");

        } else if (currentHashedPassword.equals(formHashedNewPassword)){
            throw new InvalidPasswordException("The password entered matches the current one!");
        }

        String newSalt = hasher.generateSalt();
        user.getHash().setSalt(newSalt);

        String newHashedPassword = hasher.hashPassword(
                changePasswordDto.getNewPassword(), newSalt);
        user.getHash().setHashedPassword(newHashedPassword);
        userRepository.save(user);
    }

    @Override
    public void logout(HttpSession session) {
        session.invalidate();
    }

    
}


