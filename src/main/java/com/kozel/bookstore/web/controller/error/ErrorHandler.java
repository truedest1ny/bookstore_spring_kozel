package com.kozel.bookstore.web.controller.error;

import com.kozel.bookstore.service.exception.AuthentificationException;
import com.kozel.bookstore.service.exception.AuthorizationException;
import com.kozel.bookstore.service.exception.BusinessException;
import com.kozel.bookstore.service.exception.InvalidPasswordException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    @ExceptionHandler ({MethodArgumentTypeMismatchException.class,
                        IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(HttpServletRequest request,
                                   Model model,
                                   Exception e){
        log(e);
        model.addAttribute("url", request.getRequestURI());
        return "error/bad_request";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthentificationException (Model model, AuthentificationException e){
        log(e);
        model.addAttribute("message",
                "Oops! Incorrect login or password. Please check your login details");
        return "user/login";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAuthorizationException (AuthorizationException e){
        log(e);
        return "error/forbidden";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidPasswordException (Model model, InvalidPasswordException e){
        log(e);
        model.addAttribute("message", e.getMessage());
        return "user/change_password";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleBusinessException (RedirectAttributes attributes, BusinessException e){
        log(e);
        attributes.addFlashAttribute("message", e.getMessage());
        return "redirect:/books";
    }


    private void log(Throwable e) {
        log.error("Error occurred: ", e);
    }
}
