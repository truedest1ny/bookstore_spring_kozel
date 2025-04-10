package com.kozel.bookstore.service.dto;

import lombok.Data;


@Data
public class UserCreateDto {

    private String email;
    private String login;
    private String password;

}
