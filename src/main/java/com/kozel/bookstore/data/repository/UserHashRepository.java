package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.data.entity.UserHash;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the {@link UserHash} entity.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard
 * CRUD (Create, Read, Update, Delete) methods for the UserHash entity.
 * Spring Data JPA automatically creates an implementation of this interface at runtime,
 * which allows for database interaction without writing boilerplate code.
 * </p>
 * <p>
 * The entity type is {@link UserHash}, and the primary key type is {@link Long}.
 * </p>
 */
public interface UserHashRepository extends JpaRepository<UserHash, Long> {
}
