package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.UserRepository;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.UserCreateDto;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserLoginDto;
import com.kozel.bookstore.service.dto.UserShowingDto;
import com.kozel.bookstore.service.exception.UserNotFoundException;
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


    @Override
    public List<UserDto> getAll() {

        log.debug("Called getAll() method");

        return userRepository.findAll()
                .stream()
                .map(dataMapper::toServiceDto)
                .toList();
    }

    @Override
    public List<UserShowingDto> getUsersDtoShort() {

        log.debug("Called getUsersDtoShort() method");

        return userRepository.findAll()
                .stream()
                .map(dataMapper::toServiceShortedDto)
                .toList();
    }

    @Override
    public UserDto getById(Long id) {
            log.debug("Called getById() method");

            User user = userRepository.findById(id).orElseThrow(
                    () -> new UserNotFoundException("Cannot find user by id " + id)
            );
            return dataMapper.toServiceDto(user);


    }

    @Override
    public UserDto create(UserCreateDto userCreateDto) {

        log.debug("Called create() method");

        User entity = dataMapper.toEntity(userCreateDto);
        entity.setRole(User.Role.CUSTOMER);
        User savedUser = userRepository.save(entity);
        return dataMapper.toServiceDto(savedUser);

    }

    @Override
    public UserDto update(UserDto userDto) {

        log.debug("Called update() method");

        User entity = dataMapper.toEntity(userDto);
        User savedUser = userRepository.save(entity);
        return dataMapper.toServiceDto(savedUser);
    }

    @Override
    public void disable(Long id) {
        log.debug("Called disable() method");

        try {
            UserDto user = dataMapper.toServiceDto(
                    userRepository.findById(id).orElseThrow(
                            () -> new RuntimeException("Cannot find user (id = " + id + ")." +
                                    " There is nothing to delete. ")
                    ));
            user.setDeleted(true);
            userRepository.delete(dataMapper.toEntity(user));

        } catch (DataAccessException e){
            throw new RuntimeException("Cannot delete user (id = " + id + ")");
        }
    }

    @Override
    public UserDto login(UserLoginDto userLoginDto) {
            log.debug("Called login() method");

            User user = userRepository.findByLogin(userLoginDto.getLogin()).orElseThrow(
                    () -> new UserNotFoundException(
                            "No user with such login (" + userLoginDto.getLogin() +").")
            );
            if (!user.getPassword().equals(userLoginDto.getPassword())){
                throw new RuntimeException("Incorrect password for user with login (" + userLoginDto.getLogin() + ")!");
            }

            return dataMapper.toServiceDto(user);


    }

    @Override
    public UserDto getByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(
                () -> new UserNotFoundException("No user with such login (" + login +").")
        );
        return dataMapper.toServiceDto(user);
    }
}
