package com.kozel.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;

public interface ErrorHandler {
    CommandResult process(HttpServletRequest req, Exception e);
}
