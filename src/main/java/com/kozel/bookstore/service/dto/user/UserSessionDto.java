package com.kozel.bookstore.service.dto.user;

import lombok.Data;

/**
 * A DTO representing a user's session data.
 * This object is used to store and transfer key user information
 * required during an active session, such as the user's ID, login, and role.
 */
@Data
public class UserSessionDto {

    /**
     * The unique identifier of the user.
     */
    private Long id;

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
