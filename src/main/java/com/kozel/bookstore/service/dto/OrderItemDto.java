package com.kozel.bookstore.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private BookDto book;
    private int quantity;
    private BigDecimal price = BigDecimal.ZERO;
}
