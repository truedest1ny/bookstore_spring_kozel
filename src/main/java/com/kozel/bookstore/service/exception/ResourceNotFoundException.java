package com.kozel.bookstore.service.exception;

/**
 * Custom exception indicating that a requested resource was not found.
 * Used when an entity cannot be located by its identifier in the database.
 */
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
