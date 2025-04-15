package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.dao.UserDao;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.UserCreateDto;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserDtoShowing;
import com.kozel.bookstore.service.dto.UserLoginDto;
import com.kozel.bookstore.service.exception.UserNotFoundException;
import com.kozel.bookstore.service.mapper.DataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final DataMapper dataMapper;


    @Override
    public List<UserDto> getAll() {

        log.debug("Called getAll() method");

        return userDao.findAll()
                .stream()
                .map(dataMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDtoShowing> getUsersDtoShort() {

        log.debug("Called getUsersDtoShort() method");

        return userDao.findAll()
                .stream()
                .map(dataMapper::toDtoShorted)
                .toList();
    }

    @Override
    public UserDto getById(Long id) {
        try {
            log.debug("Called getById() method");

            User user = userDao.findById(id);
            return dataMapper.toDto(user);

        } catch (DataAccessException e){
            throw new UserNotFoundException("Cannot find user by id " + id);
        }

    }

    @Override
    public Long create(UserCreateDto userCreateDto) {

        log.debug("Called create() method");

        User entity = dataMapper.toEntity(userCreateDto);
        entity.setRole(User.Role.CUSTOMER);
        return userDao.save(entity);

    }

    @Override
    public UserDto update(UserDto userDto) {

        log.debug("Called update() method");

        User entity = dataMapper.toEntity(userDto);
        User savedUser = userDao.update(entity);
        return dataMapper.toDto(savedUser);
    }

    @Override
    public void disable(Long id) {
        log.debug("Called disable() method");

        try {

            UserDto user = dataMapper.toDto(userDao.findById(id));
            user.setDeleted(true);
            userDao.delete(dataMapper.toEntity(user));

        } catch (DataAccessException e){
            throw new RuntimeException("Cannot delete user (id = " + id + ")");
        }
    }

    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        try {
            log.debug("Called login() method");

            User user = userDao.findByLogin(userLoginDto.getLogin());
            if (!user.getPassword().equals(userLoginDto.getPassword())){
                throw new RuntimeException("Incorrect password for user with login (" + userLoginDto.getLogin() + ")!");
            }

            return dataMapper.toDto(user);

        } catch (DataAccessException e){
            throw new UserNotFoundException("No user with such login (" + userLoginDto.getLogin() +").");
        }

    }
}
