package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.UserCreateDto;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserLoginDto;
import com.kozel.bookstore.service.dto.UserShowingDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    List<UserShowingDto> getUsersDtoShort();
    UserDto getById(Long id);
    UserDto create(UserCreateDto userCreateDto);
    UserDto update(UserDto userDto);
    void disable(Long id);
    UserDto login(UserLoginDto userLoginDto);
    UserDto getByLogin(String login);

}
