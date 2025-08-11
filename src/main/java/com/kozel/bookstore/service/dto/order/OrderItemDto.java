package com.kozel.bookstore.service.dto.order;

import com.kozel.bookstore.service.dto.SortableItem;
import com.kozel.bookstore.service.dto.book.BookDto;
import lombok.Data;

import java.math.BigDecimal;

/**
 * A Data Transfer Object representing an individual item within an order.
 * This DTO holds a snapshot of a book's details at the time of the order,
 * along with the quantity and the total price for that specific item.
 * It implements the {@link SortableItem} interface to enable consistent sorting logic.
 *
 * @see SortableItem
 * @see BookDto
 */
@Data
public class OrderItemDto implements SortableItem {

    /**
     * The unique identifier of the order item.
     */
    private Long id;

    /**
     * The detailed information of the book as it was at the time of the order.
     * This represents a data snapshot rather than a live book entity.
     */
    private BookDto book;

    /**
     * The quantity of the book purchased in the order.
     */
    private int quantity;

    /**
     * The total price for this item (quantity * book price at the time of order).
     */
    private BigDecimal price = BigDecimal.ZERO;
}
