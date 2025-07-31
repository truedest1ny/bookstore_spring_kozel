package com.kozel.bookstore.service.dto.book;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class BookDto {
    private Long id;
    private String name;
    private String isbn;
    private Cover cover;
    private String author;
    private Integer publishedYear;
    private BigDecimal price;
    private boolean isDeleted;

    public enum Cover {
        SOFT,
        HARD,
        SPECIAL,
    }

}
