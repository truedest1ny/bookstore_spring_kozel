package com.kozel.bookstore.controller;

import com.kozel.bookstore.service.exception.AuthentificationException;
import com.kozel.bookstore.service.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e){
        log(e);
        return "error/error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(HttpServletRequest request,
                                          Model model,
                                          ResourceNotFoundException e){
        log(e);
        model.addAttribute("url", request.getRequestURI());
        return "error/not_found";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleSqlException(DataAccessException e){
        log(e);
        return "error/sql_error";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTypeMismatchException (HttpServletRequest request,
                                               Model model,
                                               MethodArgumentTypeMismatchException e){
        log(e);
        model.addAttribute("url", request.getRequestURI());
        return "error/incorrect_input";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthentificationException (Model model, AuthentificationException e){
        log(e);
        model.addAttribute("message",
                "Oops! Incorrect login or password. Please check your login details");
        return "user/login";
    }



    private void log(Throwable e) {
        log.error("Error occurred: ", e);
    }
}
