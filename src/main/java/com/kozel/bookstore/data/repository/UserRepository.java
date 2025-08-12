package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.aspect.QueryFilterAspect;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.data.repository.annotation.QueryDeletedFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A Spring Data JPA repository for the {@link User} entity.
 * This repository handles all data access operations related to users.
 * Most of its query methods automatically exclude soft-deleted users
 * via the {@link QueryFilterAspect}, thus implementing soft-delete logic.
 *
 * @see User
 * @see QueryDeletedFilter
 * @see QueryFilterAspect
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    /**
     * Finds a single user by their email address.
     * The {@link QueryDeletedFilter} annotation ensures that only a non-deleted
     * user will be returned.
     *
     * @param email The email address of the user to find.
     * @return An {@link Optional} containing the user, or empty if not found or if the user is deleted.
     */
    @QueryDeletedFilter(isDeleted = false)
    Optional<User> findByEmail(String email);

    /**
     * Finds a paginated list of users by their last name.
     * The {@link QueryDeletedFilter} annotation ensures that only non-deleted
     * users are included in the list.
     *
     * @param pageable The pagination information.
     * @param lastName The last name to search for.
     * @return A {@link Page} of users matching the last name.
     */
    @QueryDeletedFilter(isDeleted = false)
    Page<User> findByLastName(@NonNull Pageable pageable, String lastName);

    /**
     * Finds a single user by their unique login.
     * The {@link QueryDeletedFilter} annotation ensures that only a non-deleted
     * user will be returned.
     *
     * @param login The login of the user to find.
     * @return An {@link Optional} containing the user, or empty if not found or if the user is deleted.
     */
    @QueryDeletedFilter(isDeleted = false)
    Optional<User> findByLogin (String login);

    /**
     * Checks for the existence of a user with the specified login.
     *
     * <p>This method uses the {@code @QueryDeletedFilter(isDeleted = false)} annotation,
     * which ensures that the search results will only include "active" users
     * (those not marked as deleted).
     *
     * @param login A string containing the user's login to search for. This is a mandatory parameter.
     * @return {@code true} if a user with that login
     * and {@code isDeleted = false} exists. Returns {@code false} if no such user is found.
     */
    @QueryDeletedFilter(isDeleted = false)
    boolean existsByLogin (String login);

    /**
     * Counts the total number of active (non-deleted) users in the repository.
     * The {@link QueryDeletedFilter} ensures that soft-deleted rows are not counted.
     *
     * @return The total count of active users.
     */
    @QueryDeletedFilter(isDeleted = false)
    long count();

    /**
     * Permanently deletes all soft-deleted user rows from the database.
     * This is a "hard" delete operation and should be used with caution.
     * This method does not use the soft-delete filter, as its purpose is to
     * specifically operate on deleted records.
     */
    @Modifying
    @Query("DELETE FROM User u WHERE u.isDeleted = true")
    void clearDeletedRows();

    /**
     * Finds a user by their unique ID.
     * The {@link QueryDeletedFilter} annotation ensures that only a non-deleted
     * user will be returned.
     *
     * @param id The ID of the user to find.
     * @return An {@link Optional} containing the user, or empty if the user is not found or is deleted.
     */
    @NonNull
    @QueryDeletedFilter(isDeleted = false)
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findById(@NonNull Long id);

    /**
     * Returns a {@link Page} of all active (non-deleted) users.
     * The {@link QueryDeletedFilter} automatically filters out soft-deleted users.
     *
     * @param pageable The pagination information.
     * @return A page of active users.
     */
    @NonNull
    @QueryDeletedFilter(isDeleted = false)
    Page<User> findAll(@NonNull Pageable pageable);

    /**
     * Performs a "soft" delete on a user by setting their {@code isDeleted} flag to {@code true}.
     * The record is not physically removed from the database.
     *
     * @param id The ID of the user to soft-delete.
     */
    @Modifying
    @Query("UPDATE User u SET u.isDeleted = true WHERE u.id = :id")
    void softDelete(Long id);

}
