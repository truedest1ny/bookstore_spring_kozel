package com.kozel.bookstore.service.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for creating a new user.
 * Used by API endpoints to receive user registration data.
 */
@Getter
@Setter
@EqualsAndHashCode
public class UserCreateDto {

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The unique login name of the user.
     */
    private String login;

    /**
     * The user's password.
     */
    private String password;

}
