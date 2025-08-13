package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.data.entity.UserHash;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.UserHashRepository;
import com.kozel.bookstore.data.repository.UserRepository;
import com.kozel.bookstore.service.Hasher;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.annotation.SecuredLogging;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserHashRepository userHashRepository;
    private final DataMapper mapper;
    private final Hasher hasher;

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    public Page<UserShowingDto> getUsersDtoShort(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(mapper::toShortedDto);
    }

    @Override
    public UserDto getById(Long id) {
            User user = userRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find user by id " + id)
            );
            return mapper.toDto(user);
    }

    @Override
    @SecuredLogging
    public UserDto getByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(
                () -> new ResourceNotFoundException("No user with such login (" + login +").")
        );
        return mapper.toDto(user);
    }

    @Override
    @SecuredLogging
    public UserDto create(UserCreateDto userCreateDto) {

        if (userRepository.existsByLogin(userCreateDto.getLogin())){
            throw new AuthentificationException(
                    "This login is already taken. Please choose another one.");
        }

        User newUser = collectNewUser(userCreateDto);
        User savedUser = userRepository.save(newUser);

        return mapper.toDto(savedUser);
    }

    @Override
    @SecuredLogging
    public UserDto update(UserUpdateDto dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User " + dto.getId() + " not found for update"));

        mapper.mapToEntity(dto, user);

        return mapper.toDto(
                userRepository.save(user));
    }

    @Override
    public void disable(Long id) {
        if (!userRepository.existsById(id)){
            throw  new ResourceNotFoundException("Cannot find user (id = " + id + ")." +
                    " Nothing to delete. ");
        }
            userRepository.softDelete(id);
    }

    @Override
    @SecuredLogging
    public UserDto login(UserLoginDto userLoginDto) {

        String errorAuthMessage = "Incorrect login or password. Please try again.";

            User user = userRepository.findByLogin(userLoginDto.getLogin()).orElseThrow(
                    () -> new AuthentificationException(
                            errorAuthMessage)
            );

        UserHash hash = userHashRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("UserHash not found for user ID: " + user.getId())
        );

            String userSalt = hash.getSalt();
            String inputtedPassword = userLoginDto.getPassword();
            String userHashedPassword = hash.getHashedPassword();

            if(!hasher.hashPassword(inputtedPassword, userSalt)
                    .equals(userHashedPassword)) {
             throw new AuthentificationException(
                     errorAuthMessage);
            }

            return mapper.toDto(user);
    }

    @Override
    @SecuredLogging
    public void changePassword(UserChangePasswordDto changePasswordDto){
        User user = userRepository.findById(changePasswordDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        "No user with such ID (" + changePasswordDto.getId() +").")
        );

        UserHash hash = userHashRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("UserHash not found for user ID: " + user.getId())
        );

        String currentHashedPassword = hash.getHashedPassword();

        String formHashedCurrentPassword = hasher.hashPassword(
                changePasswordDto.getCurrentPassword(),
                hash.getSalt());

        String formHashedNewPassword = hasher.hashPassword(
                changePasswordDto.getNewPassword(),
                hash.getSalt());

        if (!changePasswordDto.getNewPassword().
                equals(changePasswordDto.getConfirmPassword())){
            throw new InvalidPasswordException("The entered passwords do not match!");

        } else if (!currentHashedPassword.equals(formHashedCurrentPassword)){
            throw new InvalidPasswordException("Typed wrong current password!");

        } else if (currentHashedPassword.equals(formHashedNewPassword)){
            throw new InvalidPasswordException("The password entered matches the current one!");
        }

        String newSalt = hasher.generateSalt();
        hash.setSalt(newSalt);

        String newHashedPassword = hasher.hashPassword(
                changePasswordDto.getNewPassword(), newSalt);
        hash.setHashedPassword(newHashedPassword);
        userRepository.save(user);
    }

    private User collectNewUser(UserCreateDto userCreateDto) {
        User newUser = mapper.toEntity(userCreateDto);
        newUser.setRole(User.Role.CUSTOMER);

        User savedUser = userRepository.save(newUser);
        UserHash hash = new UserHash();

        String salt = hasher.generateSalt();
        String password = userCreateDto.getPassword();
        String hashedPassword = hasher.hashPassword(password, salt);

        hash.setSalt(salt);
        hash.setHashedPassword(hashedPassword);
        hash.setUser(savedUser);
        userHashRepository.save(hash);
        return savedUser;
    }
}


