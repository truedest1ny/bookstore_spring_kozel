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
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.kozel.bookstore.util.WebConstants.*;

/**
 * Global exception handler for controllers.
 * This class uses the {@code @ControllerAdvice} annotation to provide centralized
 * exception handling across all controllers. It maps various exceptions to
 * appropriate HTTP status codes and custom error pages.
 */
@ControllerAdvice
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
     * Handles custom authentication exceptions by redirecting the user.
     * <p>
     * It uses the {@code redirectToLastPage} helper method to redirect the user
     * to the previous page or to the login page if the "Referer" header is not present.
     * The exception message is passed as a flash attribute.
     * </p>
     *
     * @param request The HttpServletRequest to get the Referer header.
     * @param attributes The RedirectAttributes to pass the flash message.
     * @param e The authentication exception.
     * @return A redirect string to the user's previous page or the login page.
     */
    @ExceptionHandler
    public String handleAuthentificationException (HttpServletRequest request,
                                                   RedirectAttributes attributes,
                                                   AuthentificationException e){
        return redirectToLastPage
                (request, attributes, e, "/login");
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
     * <p>
     * It uses the {@code redirectToLastPage} helper method to redirect the user
     * to the change password page with an error message passed as a flash attribute.
     * </p>
     *
     * @param request The HttpServletRequest to get the Referer header.
     * @param attributes The RedirectAttributes to pass the flash message.
     * @param e The invalid password exception.
     * @return A redirect string to the user's previous page or the password change page.
     */
    @ExceptionHandler
    public String handleInvalidPasswordException (HttpServletRequest request,
                                                  RedirectAttributes attributes,
                                                  InvalidPasswordException e){
        return redirectToLastPage
                (request, attributes, e, "/profile/edit/password");
    }

    /**
     * Handles business logic exceptions that require a redirection.
     * <p>
     * It uses the {@code redirectToLastPage} helper method to redirect the user
     * to the previous page or to the home page with an error message passed as a flash attribute.
     * </p>
     *
     * @param request The HttpServletRequest to get the Referer header.
     * @param attributes The RedirectAttributes to pass a flash message.
     * @param e The business exception.
     * @return A redirect string to the user's previous page or the home page.
     */
    @ExceptionHandler
    public String handleBusinessException (HttpServletRequest request,
                                           RedirectAttributes attributes,
                                           BusinessException e){
        return redirectToLastPage
                (request, attributes, e, "/");
    }

    /**
     * Handles cases where the HTTP method is not supported for a given URL.
     * This method catches {@link HttpRequestMethodNotSupportedException} and
     * redirects the user to the last visited page with a custom error message.
     *
     * @param request The HttpServletRequest to get the Referer header.
     * @param attributes The RedirectAttributes to pass a flash message.
     * @param e The exception that was thrown.
     * @return A redirect string to the user's previous page or the home page.
     */
    @ExceptionHandler
    public String handleMethodNotSupportedException (HttpServletRequest request,
                                                     RedirectAttributes attributes,
                                                     HttpRequestMethodNotSupportedException e){
        return redirectToLastPage
                (request, attributes, e, METHOD_NOT_SUPPORTED_MESSAGE_VALUE, "/");
    }

    /**
     * Helper method to redirect the user to the last visited page.
     * <p>
     * This method checks for the "Referer" header to find the previous URL. If it's
     * not present, it redirects to a specified base URL. The exception is logged,
     * and its message is added to the {@code RedirectAttributes} as a flash attribute.
     * </p>
     *
     * @param request The HttpServletRequest to get the Referer header.
     * @param attributes The RedirectAttributes to add the flash message.
     * @param e The exception that triggered the redirect.
     * @param baseUrl The default URL to redirect to if the "Referer" header is missing.
     * @return A redirect string to the last page or the base URL.
     */
    private String redirectToLastPage(HttpServletRequest request,
                                      RedirectAttributes attributes,
                                      Throwable e,
                                      String baseUrl) {
        log(e);
        String referer = request.getHeader("Referer");
        String redirectUrl = (referer != null) ? referer : baseUrl;
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY, e.getMessage());
        return "redirect:" + redirectUrl;
    }

    /**
     * Overloaded helper method to redirect the user with a custom error message.
     * <p>
     * This method is similar to the main {@code redirectToLastPage} but uses a
     * provided custom error message instead of the exception's message. The exception
     * is still logged for debugging purposes.
     * </p>
     *
     * @param request The HttpServletRequest to get the Referer header.
     * @param attributes The RedirectAttributes to add the flash message.
     * @param e The exception that triggered the redirect.
     * @param errorMessage The custom error message to display.
     * @param baseUrl The default URL to redirect to if the "Referer" header is missing.
     * @return A redirect string to the last page or the base URL.
     */
    private String redirectToLastPage(HttpServletRequest request,
                                      RedirectAttributes attributes,
                                      Throwable e,
                                      String errorMessage,
                                      String baseUrl) {
        log(e);
        String referer = request.getHeader("Referer");
        String redirectUrl = (referer != null) ? referer : baseUrl;
        attributes.addFlashAttribute(ERROR_MESSAGE_KEY, errorMessage);
        return "redirect:" + redirectUrl;
    }

    private void log(Throwable e) {
        log.error("Error occurred: ", e);
    }
}
