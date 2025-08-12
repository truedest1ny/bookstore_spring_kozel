package com.kozel.bookstore.service.dto.book;

import lombok.Data;

import java.math.BigDecimal;

/**
 * A Data Transfer Object representing a book's detailed information.
 * This DTO is used for retrieving and displaying comprehensive book data
 * from the API, including its ID, name, author, and price.
 *
 */
@Data
public class BookDto {

    /**
     * The unique identifier of the book.
     */
    private Long id;

    /**
     * The name or title of the book.
     */
    private String name;

    /**
     * The unique ISBN of the book.
     */
    private String isbn;

    /**
     * The type of cover the book has.
     * @see Cover
     */
    private Cover cover;

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

    /**
     * A flag indicating if the book has been soft-deleted.
     */
    private boolean isDeleted;

    /**
     * Represents the different types of book covers available.
     */
    public enum Cover {
        SOFT,
        HARD,
        SPECIAL,
    }

}
