package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.data.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<Long, User>{
    User getReferenceById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findByLastName(String lastName);
    Optional<User> findByLogin (String login);
    long countAll();
    void clearDeletedRows();
}
