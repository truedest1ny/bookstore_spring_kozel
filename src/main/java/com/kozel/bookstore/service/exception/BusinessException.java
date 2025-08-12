package com.kozel.bookstore.service.exception;

/**
 * Custom exception for business logic failures.
 * This exception is thrown when an operation violates a business rule,
 * such as an invalid order status change or an attempt to modify an immutable resource.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }
}
