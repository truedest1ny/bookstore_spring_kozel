package com.kozel.bookstore.service.dto;

import com.kozel.bookstore.service.dto.book.BookDto;

import java.math.BigDecimal;

public interface SortableItem {
        BookDto getBook();
        int getQuantity();
        BigDecimal getPrice();
}
