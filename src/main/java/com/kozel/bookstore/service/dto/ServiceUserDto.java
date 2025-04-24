package com.kozel.bookstore.service.dto;


import lombok.Data;


@Data
public class ServiceUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;
    private Role role;
    private boolean isDeleted;

    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        MANAGER,
        CUSTOMER,
    }


}
