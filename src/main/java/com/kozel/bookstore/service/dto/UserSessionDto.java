package com.kozel.bookstore.service.dto;

import lombok.Data;

@Data
public class UserSessionDto {

    private Long id;
    private String login;
    private Role role;

    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        MANAGER,
        CUSTOMER,
    }
}
