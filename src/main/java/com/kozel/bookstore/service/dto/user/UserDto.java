package com.kozel.bookstore.service.dto.user;


import lombok.Data;

/**
 * DTO for transferring user details.
 * This object is used to represent a user's profile information,
 * including identity, contact details, and role.
 */
@Data
public class UserDto {

    /**
     * The unique identifier of the user.
     */
    private Long id;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The login name of the user.
     */
    private String login;

    /**
     * The role assigned to the user.
     * @see Role
     */
    private Role role;

    /**
     * A flag indicating if the user has been soft-deleted.
     */
    private boolean isDeleted;

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
