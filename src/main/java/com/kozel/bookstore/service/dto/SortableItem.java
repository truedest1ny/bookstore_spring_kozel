package com.kozel.bookstore.service.dto;

import com.kozel.bookstore.service.dto.book.BookDto;

import java.math.BigDecimal;

/**
 * An interface defining the contract for items that can be sorted or paginated.
 * This interface is a key part of a generic pagination mechanism, providing
 * access to common item properties like the associated book, quantity, and price.
 */
public interface SortableItem {

        /**
         * Retrieves the book associated with the item.
         * @return The {@link BookDto} object.
         */
        BookDto getBook();

        /**
         * Retrieves the quantity of the item.
         * @return The item quantity.
         */
        int getQuantity();

        /**
         * Retrieves the total price of the item.
         * @return The total price as a {@link BigDecimal}.
         */
        BigDecimal getPrice();
}
