package com.kozel.bookstore.service.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class BookShowingDto {

    private Long id;
    private String name;
    private String author;
    private Integer publishedYear;
    private BigDecimal price;

}
