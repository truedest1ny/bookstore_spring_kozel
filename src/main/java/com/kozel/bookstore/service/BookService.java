package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.book.BookDto;
import com.kozel.bookstore.service.dto.book.BookShowingDto;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    List<BookDto> getAll();
    List<BookShowingDto> getBooksDtoShort();
    BookDto getById(Long id);
    BookDto create(BookDto bookDto);
    BookDto update(BookDto bookDto);
    void disable(Long id);
    BigDecimal getSumPriceByAuthor (String author);

}
