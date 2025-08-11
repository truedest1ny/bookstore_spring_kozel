package com.kozel.bookstore.service.dto.order;

import com.kozel.bookstore.service.dto.user.UserDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Data Transfer Object representing a detailed order.
 * This DTO is used to transfer comprehensive order data, including the associated user,
 * a full list of items, the total price, and the order's current status.
 */
@Data
public class OrderDto {

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
     * This field is typically populated by a custom mapper method.
     */
    private String formattedDate;

    /**
     * The user who placed the order.
     */
    private UserDto user;

    /**
     * A set of items included in the order.
     * The set is initialized to prevent NullPointerExceptions.
     */
    private Set<OrderItemDto> items = new HashSet<>();

    /**
     * The current status of the order.
     * @see Status
     */
    private Status status;

    /**
     * The total price of all items in the order.
     */
    private BigDecimal totalPrice = BigDecimal.ZERO;

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

    /**
     * Adds a new item to the order's set of items.
     *
     * @param item The {@link OrderItemDto} to add.
     */
    public void addItem(OrderItemDto item) {
        items.add(item);
    }
}
