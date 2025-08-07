package com.kozel.bookstore.service.dto.order;

import com.kozel.bookstore.service.dto.user.UserDto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private LocalDateTime date;
    private String formattedDate;
    private UserDto user;
    private Set<OrderItemDto> items = new HashSet<>();
    private Status status;
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public enum Status {
        PENDING,
        PAID,
        DELIVERED,
        CANCELLED,
        ARCHIVED,
    }

    public void addItem(OrderItemDto item) {
        items.add(item);
    }
}
