package com.kozel.bookstore.service.dto.book;

import lombok.Data;

import java.math.BigDecimal;

/**
 * A Data Transfer Object representing a simplified view of a book.
 * This DTO is used for displaying books in lists, search results,
 * or any context where only essential information is required.
 * It omits sensitive or non-critical details like the ISBN, cover type, or soft-delete status
 * to reduce payload size and focus on key display data.
 */
@Data
public class BookShowingDto {

    /**
     * The unique identifier of the book.
     */
    private Long id;

    /**
     * The name or title of the book.
     */
    private String name;

    /**
     * The author of the book.
     */
    private String author;

    /**
     * The year the book was published.
     */
    private Integer publishedYear;

    /**
     * The price of the book.
     */
    private BigDecimal price;

}
