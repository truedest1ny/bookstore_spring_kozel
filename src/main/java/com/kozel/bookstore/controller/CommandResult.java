package com.kozel.bookstore.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommandResult {
    private String page;
    private int statusCode;
}
