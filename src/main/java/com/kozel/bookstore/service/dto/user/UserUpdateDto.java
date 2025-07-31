package com.kozel.bookstore.service.dto.user;

import lombok.Data;

@Data
public class UserUpdateDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        MANAGER,
        CUSTOMER,
    }
}
