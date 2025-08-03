package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.data.repository.annotation.QueryDeletedFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    @QueryDeletedFilter(isDeleted = false)
    Optional<User> findByEmail(String email);

    @QueryDeletedFilter(isDeleted = false)
    List<User> findByLastName(String lastName);

    @QueryDeletedFilter(isDeleted = false)
    Optional<User> findByLogin (String login);

    @QueryDeletedFilter(isDeleted = false)
    long count();

    @Modifying
    @Query("DELETE FROM User u WHERE u.isDeleted = true")
    void clearDeletedRows();

    @NonNull
    @QueryDeletedFilter(isDeleted = false)
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findById(@NonNull Long id);

    @NonNull
    @QueryDeletedFilter(isDeleted = false)
    List<User> findAll();

    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :id")
    void softDelete(Long id);

}
