package com.kozel.bookstore.service.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for user authentication.
 * This object is used to transfer user credentials for login requests.
 */
@Getter
@Setter
@EqualsAndHashCode
public class UserLoginDto {

    /**
     * The user's login name.
     */
    private String login;

    /**
     * The user's password.
     */
    private String password;
}
