package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.UserRepository;
import com.kozel.bookstore.service.UserService;
import com.kozel.bookstore.service.dto.ServiceUserCreateDto;
import com.kozel.bookstore.service.dto.ServiceUserDto;
import com.kozel.bookstore.service.dto.ServiceUserLoginDto;
import com.kozel.bookstore.service.dto.ServiceUserShowingDto;
import com.kozel.bookstore.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DataMapper dataMapper;


    @Override
    public List<ServiceUserDto> getAll() {

        log.debug("Called getAll() method");

        return userRepository.findAll()
                .stream()
                .map(dataMapper::toServiceDto)
                .toList();
    }

    @Override
    public List<ServiceUserShowingDto> getUsersDtoShort() {

        log.debug("Called getUsersDtoShort() method");

        return userRepository.findAll()
                .stream()
                .map(dataMapper::toServiceShortedDto)
                .toList();
    }

    @Override
    public ServiceUserDto getById(Long id) {
        try {
            log.debug("Called getById() method");

            User user = userRepository.findById(id);
            return dataMapper.toServiceDto(user);

        } catch (DataAccessException e){
            throw new UserNotFoundException("Cannot find user by id " + id);
        }

    }

    @Override
    public Long create(ServiceUserCreateDto serviceUserCreateDto) {

        log.debug("Called create() method");

        User entity = dataMapper.toEntity(serviceUserCreateDto);
        entity.setRole(User.Role.CUSTOMER);
        return userRepository.save(entity);

    }

    @Override
    public ServiceUserDto update(ServiceUserDto serviceUserDto) {

        log.debug("Called update() method");

        User entity = dataMapper.toEntity(serviceUserDto);
        User savedUser = userRepository.update(entity);
        return dataMapper.toServiceDto(savedUser);
    }

    @Override
    public void disable(Long id) {
        log.debug("Called disable() method");

        try {

            ServiceUserDto user = dataMapper.toServiceDto(userRepository.findById(id));
            user.setDeleted(true);
            userRepository.delete(dataMapper.toEntity(user));

        } catch (DataAccessException e){
            throw new RuntimeException("Cannot delete user (id = " + id + ")");
        }
    }

    @Override
    public ServiceUserDto login(ServiceUserLoginDto serviceUserLoginDto) {
        try {
            log.debug("Called login() method");

            User user = userRepository.findByLogin(serviceUserLoginDto.getLogin());
            if (!user.getPassword().equals(serviceUserLoginDto.getPassword())){
                throw new RuntimeException("Incorrect password for user with login (" + serviceUserLoginDto.getLogin() + ")!");
            }

            return dataMapper.toServiceDto(user);

        } catch (DataAccessException e){
            throw new UserNotFoundException("No user with such login (" + serviceUserLoginDto.getLogin() +").");
        }

    }

    @Override
    public ServiceUserDto getByLogin(String login) {
        User user = userRepository.findByLogin(login);

        if (user == null){
            throw new UserNotFoundException("Cannot find user by login: " + login);
        }
        return dataMapper.toServiceDto(user);
    }
}
