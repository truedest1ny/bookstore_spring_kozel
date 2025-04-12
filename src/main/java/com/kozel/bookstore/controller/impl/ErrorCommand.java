package com.kozel.bookstore.controller.impl;

import com.kozel.bookstore.controller.Command;
import com.kozel.bookstore.controller.CommandResult;
import com.kozel.bookstore.service.exception.BookNotFoundException;
import com.kozel.bookstore.service.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.sql.SQLException;


public class ErrorCommand implements Command {

    @Override
    public CommandResult process(HttpServletRequest req) {
        return new CommandResult("jsp/error/error.jsp", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Override
    public CommandResult process(HttpServletRequest req, Exception e) {
        return switch (e) {
            case UserNotFoundException userNotFoundException ->
                    new CommandResult("jsp/error/404/user_not_found.jsp", HttpServletResponse.SC_NOT_FOUND);

            case BookNotFoundException bookNotFoundException ->
                    new CommandResult("jsp/error/404/book_not_found.jsp", HttpServletResponse.SC_NOT_FOUND);

            case SQLException sqlException ->
                    new CommandResult("jsp/error/sql_error.jsp", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            case NoSuchBeanDefinitionException noSuchBeanDefinitionException ->
                    new CommandResult("jsp/error/404/command_not_found.jsp", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            case NumberFormatException numberFormatException ->
                    new CommandResult("jsp/error/incorrect_input.jsp", HttpServletResponse.SC_BAD_REQUEST);
            default ->
                    process(req);

        };
    }
}
