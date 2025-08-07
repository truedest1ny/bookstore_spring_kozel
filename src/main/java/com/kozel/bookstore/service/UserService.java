package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.user.UserChangePasswordDto;
import com.kozel.bookstore.service.dto.user.UserCreateDto;
import com.kozel.bookstore.service.dto.user.UserDto;
import com.kozel.bookstore.service.dto.user.UserLoginDto;
import com.kozel.bookstore.service.dto.user.UserShowingDto;
import com.kozel.bookstore.service.dto.user.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDto> getAll(Pageable pageable);
    Page<UserShowingDto> getUsersDtoShort(Pageable pageable);
    UserDto getById(Long id);
    UserDto create(UserCreateDto userCreateDto);
    UserDto update(UserUpdateDto dto);
    void disable(Long id);
    UserDto login(UserLoginDto userLoginDto);
    void changePassword(UserChangePasswordDto changePasswordDto);
    UserDto getByLogin(String login);
}
