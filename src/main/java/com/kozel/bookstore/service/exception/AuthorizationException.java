package com.kozel.bookstore.service.exception;

/**
 * Custom exception for authorization failures.
 * This exception is thrown to indicate that a user
 * does not have the necessary permissions to access a resource or perform an action.
 */
public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
