package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.UserCreateDto;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserDtoShowing;
import com.kozel.bookstore.service.dto.UserLoginDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    List<UserDtoShowing> getUsersDtoShort();
    UserDto getById(Long id);
    Long create(UserCreateDto userCreateDto);
    UserDto update(UserDto userDto);
    void disable(Long id);
    UserDto login(UserLoginDto userLoginDto);

}
