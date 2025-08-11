package com.kozel.bookstore.service.dto.cart;

import com.kozel.bookstore.service.dto.SortableItem;
import com.kozel.bookstore.service.dto.book.BookDto;
import lombok.Data;

import java.math.BigDecimal;

/**
 * A Data Transfer Object representing an item in a shopping cart.
 * This DTO holds information about a specific book, its quantity, and the total price
 * for that item. It implements the {@link SortableItem} interface to allow
 * for consistent sorting and pagination logic across different item types.
 *
 * @see SortableItem
 * @see BookDto
 */
@Data
public class CartItemDto implements SortableItem {

    /**
     * The unique identifier of the cart item.
     */
    private Long id;

    /**
     * The detailed information of the book associated with this cart item.
     */
    private BookDto book;

    /**
     * The quantity of the book in the cart.
     */
    private int quantity;

    /**
     * The total price for this item (quantity * book price).
     */
    private BigDecimal price = BigDecimal.ZERO;
}
