package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.data.entity.User;

import java.util.List;

public interface UserRepository extends CrudRepository<Long, User>{
    User findByEmail(String email);
    List<User> findByLastName(String lastName);
    User findByLogin (String login);
    long countAll();
    long clearDeletedRows();
}
