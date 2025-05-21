package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.ServiceUserCreateDto;
import com.kozel.bookstore.service.dto.ServiceUserDto;
import com.kozel.bookstore.service.dto.ServiceUserLoginDto;
import com.kozel.bookstore.service.dto.ServiceUserShowingDto;

import java.util.List;

public interface UserService {
    List<ServiceUserDto> getAll();
    List<ServiceUserShowingDto> getUsersDtoShort();
    ServiceUserDto getById(Long id);
    ServiceUserDto create(ServiceUserCreateDto serviceUserCreateDto);
    ServiceUserDto update(ServiceUserDto serviceUserDto);
    void disable(Long id);
    ServiceUserDto login(ServiceUserLoginDto serviceUserLoginDto);
    ServiceUserDto getByLogin(String login);

}
