package com.kozel.bookstore.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ServiceOrderShowingDto {
    private Long id;
    private LocalDateTime date;
    private String userLogin;
    private ServiceOrderShowingDto.Status status;
    private BigDecimal totalPrice;

    public enum Status {
        PENDING,
        PAID,
        DELIVERED,
        CANCELLED,
    }
}
