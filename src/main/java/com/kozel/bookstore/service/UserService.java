package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.user.UserChangePasswordDto;
import com.kozel.bookstore.service.dto.user.UserCreateDto;
import com.kozel.bookstore.service.dto.user.UserDto;
import com.kozel.bookstore.service.dto.user.UserLoginDto;
import com.kozel.bookstore.service.dto.user.UserShowingDto;
import com.kozel.bookstore.service.dto.user.UserUpdateDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    List<UserShowingDto> getUsersDtoShort();
    UserDto getById(Long id);
    UserDto create(UserCreateDto userCreateDto);
    UserDto update(UserUpdateDto dto);
    void disable(Long id);
    UserDto login(UserLoginDto userLoginDto);
    void changePassword(UserChangePasswordDto changePasswordDto);
    UserDto getByLogin(String login);
}
