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

import static com.kozel.bookstore.util.WebConstants.ERROR_MESSAGE_KEY;
import static com.kozel.bookstore.util.WebConstants.URL_KEY;

/**
 * Global exception handler for controllers.
 * This class uses the {@code @ControllerAdvice} annotation to provide centralized
 * exception handling across all controllers. It maps various exceptions to
 * appropriate HTTP status codes and custom error pages.
 */
@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class ErrorHandler {

    /**
     * Handles generic exceptions not covered by other handlers.
     * This is a catch-all method for unexpected errors, returning a
     * generic error page with an HTTP 500 status.
     *
     * @param e The exception that was thrown.
     * @return The view name for the generic error page.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e){
        log(e);
        return "error/error";
    }

    /**
     * Handles exceptions when a requested resource is not found.
     * This method catches {@link ResourceNotFoundException} and returns a
     * custom 404 Not Found page, including the original request URI in the model.
     *
     * @param request The HttpServletRequest to get the URI.
     * @param model The Spring Model to pass data to the view.
     * @param e The exception that was thrown.
     * @return The view name for the 404 Not Found page.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(HttpServletRequest request,
                                          Model model,
                                          ResourceNotFoundException e){
        log(e);
        model.addAttribute(URL_KEY, request.getRequestURI());
        return "error/not_found";
    }

    /**
     * Handles data access exceptions, such as issues with the database.
     * This method catches {@link DataAccessException} and returns a specific
     * SQL error page with an HTTP 500 status.
     *
     * @param e The data access exception that was thrown.
     * @return The view name for the SQL error page.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleSqlException(DataAccessException e){
        log(e);
        return "error/sql_error";
    }

    /**
     * Handles exceptions related to bad requests from the client.
     * This method handles both {@link MethodArgumentTypeMismatchException} and
     * {@link IllegalArgumentException}, returning a custom 400 Bad Request page.
     *
     * @param request The HttpServletRequest to get the URI.
     * @param model The Spring Model to pass data to the view.
     * @param e The exception that was thrown.
     * @return The view name for the bad request page.
     */
    @ExceptionHandler ({MethodArgumentTypeMismatchException.class,
                        IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(HttpServletRequest request,
                                   Model model,
                                   Exception e){
        log(e);
        model.addAttribute(URL_KEY, request.getRequestURI());
        return "error/bad_request";
    }

    /**
     * Handles authentication exceptions, typically during the login process.
     * It returns a custom login page with a 401 Unauthorized status and a user-friendly message.
     *
     * @param model The Spring Model to pass the error message to the view.
     * @param e The authentication exception that was thrown.
     * @return The view name for the user login page.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleAuthentificationException (Model model, AuthentificationException e){
        log(e);
        model.addAttribute(ERROR_MESSAGE_KEY, e.getMessage());
        return "user/login";
    }

    /**
     * Handles authorization exceptions, indicating insufficient permissions.
     * This method catches {@link AuthorizationException} and returns a custom
     * 403 Forbidden page.
     *
     * @param e The authorization exception that was thrown.
     * @return The view name for the forbidden error page.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAuthorizationException (AuthorizationException e){
        log(e);
        return "error/forbidden";
    }

    /**
     * Handles exceptions related to invalid password scenarios.
     * It returns the change password page with a 400 Bad Request status
     * and a specific error message for the user.
     *
     * @param model The Spring Model to pass the error message to the view.
     * @param e The invalid password exception that was thrown.
     * @return The view name for the change password page.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidPasswordException (Model model, InvalidPasswordException e){
        log(e);
        model.addAttribute(ERROR_MESSAGE_KEY, e.getMessage());
        return "user/change_password";
    }

    /**
     * Handles business logic exceptions that require a redirection.
     * This method catches {@link BusinessException} and redirects the user,
     * adding the error message as a flash attribute to be displayed on the next page.
     *
     * @param attributes The RedirectAttributes to pass a flash message.
     * @param e The business exception that was thrown.
     * @return The redirect URL to the books page.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleBusinessException (RedirectAttributes attributes, BusinessException e){
        log(e);
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY, e.getMessage());
        return "redirect:/books";
    }


    private void log(Throwable e) {
        log.error("Error occurred: ", e);
    }
}
