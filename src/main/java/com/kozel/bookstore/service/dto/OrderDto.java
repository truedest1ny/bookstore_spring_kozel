package com.kozel.bookstore.service.dto;

import com.kozel.bookstore.data.entity.OrderItem;
import com.kozel.bookstore.data.entity.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private LocalDateTime date;
    private User user;
    private List<OrderItem> items;
    private Status status;
    private BigDecimal totalPrice;

    public enum Status {
        PENDING,
        PAID,
        DELIVERED,
        CANCELLED,
    }
}
