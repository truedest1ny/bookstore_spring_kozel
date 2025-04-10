package com.kozel.bookstore.service.exception;

public class IllegalCommandException extends RuntimeException {
    public IllegalCommandException(String message) {
        super(message);
    }
}
