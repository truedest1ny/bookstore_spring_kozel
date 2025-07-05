package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.UserCreateDto;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserLoginDto;
import com.kozel.bookstore.service.dto.UserShowingDto;
import com.kozel.bookstore.service.dto.UserUpdateDto;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    List<UserShowingDto> getUsersDtoShort();
    UserDto getById(Long id);
    UserDto create(UserCreateDto userCreateDto);
    UserDto update(UserUpdateDto dto);
    void disable(Long id);
    UserDto login(UserLoginDto userLoginDto);
    UserDto getByLogin(String login);
    void logout(HttpSession session);

}
