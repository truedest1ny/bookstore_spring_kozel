package com.kozel.bookstore.data.dao;

import com.kozel.bookstore.data.entity.User;

import java.util.List;

public interface UserDao extends CrudDao<Long, User>{
    User findByEmail(String email);
    List<User> findByLastName(String lastName);
    User findByLogin (String login);
    long countAll();
}
