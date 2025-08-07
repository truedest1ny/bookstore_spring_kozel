package com.kozel.bookstore.service.dto.cart;

import com.kozel.bookstore.service.dto.SortableItem;
import com.kozel.bookstore.service.dto.book.BookDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto implements SortableItem {
    private Long id;
    private BookDto book;
    private int quantity;
    private BigDecimal price = BigDecimal.ZERO;
}
