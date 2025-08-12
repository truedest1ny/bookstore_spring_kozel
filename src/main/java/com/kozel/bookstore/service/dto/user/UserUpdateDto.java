package com.kozel.bookstore.service.dto.user;

import lombok.Data;

/**
 * A DTO for updating user details.
 * This object is used by API endpoints to receive updated user data,
 * including profile and role information.
 */
@Data
public class UserUpdateDto {

    /**
     * The unique identifier of the user to be updated.
     */
    private Long id;

    /**
     * The new first name of the user.
     */
    private String firstName;

    /**
     * The new last name of the user.
     */
    private String lastName;

    /**
     * The new email address of the user.
     */
    private String email;

    /**
     * The new role for the user.
     * @see Role
     */
    private Role role;

    /**
     * Represents the possible roles a user can have.
     */
    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        MANAGER,
        CUSTOMER,
    }
}
