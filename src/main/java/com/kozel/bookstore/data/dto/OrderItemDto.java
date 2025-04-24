package com.kozel.bookstore.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private Long bookId;
    private Long quantity;
    private BigDecimal price;
    private Long orderId;
}
