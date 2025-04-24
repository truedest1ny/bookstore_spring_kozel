package com.kozel.bookstore.service.dto;

import lombok.Data;


@Data
public class ServiceUserShowingDto {

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
