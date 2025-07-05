package com.kozel.bookstore.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UserChangePasswordDto {
    private Long id;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
