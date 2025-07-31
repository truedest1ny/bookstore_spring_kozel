package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.data.entity.UserHash;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.UserRepository;
import com.kozel.bookstore.service.Hasher;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.user.UserChangePasswordDto;
import com.kozel.bookstore.service.dto.user.UserCreateDto;
import com.kozel.bookstore.service.dto.user.UserDto;
import com.kozel.bookstore.service.dto.user.UserLoginDto;
import com.kozel.bookstore.service.dto.user.UserShowingDto;
import com.kozel.bookstore.service.dto.user.UserUpdateDto;
import com.kozel.bookstore.service.exception.AuthentificationException;
import com.kozel.bookstore.service.exception.InvalidPasswordException;
import com.kozel.bookstore.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DataMapper mapper;
    private final Hasher hasher;


    @Override
    public List<UserDto> getAll() {
        log.debug("Called getAll() method");
        return mapper.toUserDtoList(
                userRepository.findAll());
    }

    @Override
    public List<UserShowingDto> getUsersDtoShort() {
        log.debug("Called getUsersDtoShort() method");
        return mapper.toUserShowingDtoList(
                userRepository.findAll());
    }

    @Override
    public UserDto getById(Long id) {
            log.debug("Called getById() method");
            User user = userRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find user by id " + id)
            );
            return mapper.toDto(user);
    }

    @Override
    public UserDto getByLogin(String login) {
        log.debug("Called getByLogin() method");
        User user = userRepository.findByLogin(login).orElseThrow(
                () -> new ResourceNotFoundException("No user with such login (" + login +").")
        );
        return mapper.toDto(user);
    }

    @Override
    public UserDto create(UserCreateDto userCreateDto) {
        log.debug("Called create() method");

        User newUser = collectNewUser(userCreateDto);
        User savedUser = userRepository.save(newUser);

        return mapper.toDto(savedUser);
    }

    @Override
    public UserDto update(UserUpdateDto dto) {
        log.debug("Called update() method");

        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User " + dto.getId() + " not found for update"));

        mapper.mapToEntity(dto, user);

        return mapper.toDto(
                userRepository.save(user));
    }

    @Override
    public void disable(Long id) {
        log.debug("Called disable() method");
            User userToDisable = userRepository.findById(id).orElseThrow(
                            () -> new RuntimeException("Cannot find user (id = " + id + ")." +
                                    " Nothing to delete. "));
            userRepository.delete(userToDisable);
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
             throw new AuthentificationException(
                     "Incorrect password for user (" + user.getLogin() + ")");
            }

            return mapper.toDto(user);
    }

    @Override
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

    private User collectNewUser(UserCreateDto userCreateDto) {
        User newUser = mapper.toEntity(userCreateDto);
        newUser.setRole(User.Role.CUSTOMER);

        UserHash hash = new UserHash();

        String salt = hasher.generateSalt();
        String password = userCreateDto.getPassword();
        String hashedPassword = hasher.hashPassword(password, salt);

        hash.setSalt(salt);
        hash.setHashedPassword(hashedPassword);

        newUser.setHash(hash);
        hash.setUser(newUser);
        return newUser;
    }
}


