package com.kozel.bookstore.service.exception;

/**
 * Custom exception for invalid password scenarios.
 * This exception is thrown when a provided password does not meet security
 * requirements or is incorrect for the associated user.
 * It covers various validation failures during password change.
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
