package com.kozel.bookstore.service.dto.order;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A Data Transfer Object representing a simplified view of an order.
 * This DTO is designed for displaying orders in a list or table, where
 * only a summary of information is needed. It includes key details like the
 * order ID, date, status, and total price, along with the associated user's login.
 */
@Data
public class OrderShowingDto {

    /**
     * The unique identifier of the order.
     */
    private Long id;

    /**
     * The date and time when the order was created.
     */
    private LocalDateTime date;

    /**
     * A formatted string representation of the order creation date.
     * This field is typically used for direct display in the UI.
     */
    private String formattedDate;

    /**
     * The login of the user who placed the order.
     */
    private String userLogin;

    /**
     * The current status of the order.
     * @see Status
     */
    private OrderShowingDto.Status status;

    /**
     * The total price of all items in the order.
     */
    private BigDecimal totalPrice;

    /**
     * Represents the possible statuses an order can have.
     */
    public enum Status {
        PENDING,
        PAID,
        DELIVERED,
        CANCELLED,
        ARCHIVED,
    }
}
