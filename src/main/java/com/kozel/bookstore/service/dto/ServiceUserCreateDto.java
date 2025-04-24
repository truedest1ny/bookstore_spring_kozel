package com.kozel.bookstore.service.dto;

import lombok.Data;


@Data
public class ServiceUserCreateDto {

    private String email;
    private String login;
    private String password;

}
