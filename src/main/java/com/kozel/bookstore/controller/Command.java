package com.kozel.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {

    CommandResult process(HttpServletRequest req);
    CommandResult process(HttpServletRequest req, Exception e);
}

