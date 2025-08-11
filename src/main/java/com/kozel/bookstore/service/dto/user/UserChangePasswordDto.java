package com.kozel.bookstore.service.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for changing a user's password.
 * Used by API endpoints that handle password updates.
 */
@Getter
@Setter
@EqualsAndHashCode
public class UserChangePasswordDto {

    /**
     * The unique identifier of the user.
     */
    private Long id;

    /**
     * The user's current password for verification.
     */
    private String currentPassword;

    /**
     * The new password to be set.
     */
    private String newPassword;

    /**
     * A confirmation of the new password.
     */
    private String confirmPassword;
}
