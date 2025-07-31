package com.kozel.bookstore.service.dto.user;

import lombok.Data;


@Data
public class UserShowingDto {

    private Long id;
    private String email;
    private String login;
    private Role role;

    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        MANAGER,
        CUSTOMER,
    }


}
