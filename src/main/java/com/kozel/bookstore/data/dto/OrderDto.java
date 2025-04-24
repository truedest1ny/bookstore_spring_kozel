package com.kozel.bookstore.data.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;
    private LocalDateTime date;
    private Long userId;
    private Status status;
    private BigDecimal price;

    public enum Status {
        PENDING,
        PAID,
        DELIVERED,
        CANCELLED,
    }
}
