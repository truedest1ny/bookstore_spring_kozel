package com.kozel.bookstore.data.dao;


import com.kozel.bookstore.data.dto.UserDto;

import java.util.List;

public interface UserDao extends CrudDao<Long, UserDto> {
    UserDto findByEmail(String email);
    List<UserDto> findByLastName(String lastName);
    UserDto findByLogin (String login);
    long countAll();
    long clearDeletedRows();
}
