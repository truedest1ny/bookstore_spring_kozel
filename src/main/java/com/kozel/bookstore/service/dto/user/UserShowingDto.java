package com.kozel.bookstore.service.dto.user;

import lombok.Data;

/**
 * A DTO representing a simplified view of a user's profile.
 * This object is used for displaying user information in lists or tables,
 * where only essential details like ID, login, and role are needed.
 * It omits sensitive data such as passwords and non-critical information like first and last names.
 */
@Data
public class UserShowingDto {

    /**
     * The unique identifier of the user.
     */
    private Long id;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The login name of the user.
     */
    private String login;

    /**
     * The role of the user within the system.
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
