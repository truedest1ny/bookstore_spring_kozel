package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.user.UserChangePasswordDto;
import com.kozel.bookstore.service.dto.user.UserCreateDto;
import com.kozel.bookstore.service.dto.user.UserDto;
import com.kozel.bookstore.service.dto.user.UserLoginDto;
import com.kozel.bookstore.service.dto.user.UserShowingDto;
import com.kozel.bookstore.service.dto.user.UserUpdateDto;
import com.kozel.bookstore.service.exception.AuthentificationException;
import com.kozel.bookstore.service.exception.InvalidPasswordException;
import com.kozel.bookstore.service.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service for managing user-related business logic.
 * This contract defines methods for user authentication, profile management,
 * and password changes.
 */
public interface UserService {

    /**
     * Retrieves all users with full details in a paginated format.
     * This method is intended for administrative use.
     *
     * @param pageable The pagination information.
     * @return A paginated list of all users as {@link UserDto}.
     */
    Page<UserDto> getAll(Pageable pageable);

    /**
     * Retrieves all users with a simplified view in a paginated format.
     * This method is suitable for user lists where
     * a summary of information is sufficient.
     *
     * @param pageable The pagination information.
     * @return A paginated list of simplified users as {@link UserShowingDto}.
     */
    Page<UserShowingDto> getUsersDtoShort(Pageable pageable);

    /**
     * Retrieves a single user by their unique identifier.
     *
     * @param id The unique ID of the user.
     * @return The {@link UserDto} for the specified ID.
     * @throws ResourceNotFoundException if no user is found with the given ID.
     */
    UserDto getById(Long id);

    /**
     * Creates a new user in the system.
     * This method automatically assigns the user the {@code CUSTOMER} role and
     * securely hashes their password.
     *
     * @param userCreateDto DTO containing user creation data.
     * @return The newly created {@link UserDto}.
     */
    UserDto create(UserCreateDto userCreateDto);

    /**
     * Updates an existing user's details.
     * This method allows modification of fields such as first name, last name, email, and role.
     *
     * @param dto DTO containing the updated user data.
     * @return The updated {@link UserDto}.
     * @throws ResourceNotFoundException if the user to be updated is not found.
     */
    UserDto update(UserUpdateDto dto);

    /**
     * Performs a soft delete on a user.
     * The user is not physically removed from the database; their status
     * is marked as inactive.
     *
     * @param id The unique ID of the user to disable.
     * @throws ResourceNotFoundException if no user is found with the specified ID.
     */
    void disable(Long id);

    /**
     * Authenticates a user.
     * This method validates the provided credentials and returns the user DTO
     * upon successful login.
     *
     * @param userLoginDto DTO containing the user's login and password.
     * @return The {@link UserDto} of the authenticated user.
     * @throws AuthentificationException if the credentials are incorrect.
     */
    UserDto login(UserLoginDto userLoginDto);

    /**
     * Changes a user's password.
     * This method performs a series of checks, including verifying that
     * the new passwords match, the current password is correct, and the new
     * password is not the same as the old one.
     *
     * @param changePasswordDto DTO containing the user ID, current password, and new password.
     * @throws ResourceNotFoundException if the user is not found.
     * @throws InvalidPasswordException if the new passwords do not match, the current password
     * is incorrect, or the new password is the same as the current one.
     */
    void changePassword(UserChangePasswordDto changePasswordDto);

    /**
     * Retrieves a user by their login.
     * This method is marked for secure logging to prevent exposure of sensitive data.
     *
     * @param login The user's login.
     * @return The {@link UserDto} for the specified login.
     * @throws ResourceNotFoundException if no user is found with the given login.
     */
    UserDto getByLogin(String login);
}
