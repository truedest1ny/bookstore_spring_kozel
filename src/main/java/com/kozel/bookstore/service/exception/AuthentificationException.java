package com.kozel.bookstore.service.exception;

/**
 * Custom exception for authentication failures.
 * This exception is thrown to indicate that a login attempt
 * was unsuccessful due to invalid credentials or other authentication issues.
 */
public class AuthentificationException extends RuntimeException {
    public AuthentificationException(String message) {
        super(message);
    }
    public AuthentificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
