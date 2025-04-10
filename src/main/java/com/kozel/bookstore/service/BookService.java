package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.BookDto;
import com.kozel.bookstore.service.dto.BookDtoShowing;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    List<BookDto> getAll();
    List<BookDtoShowing> getBooksDtoShort();
    BookDto getById(Long id);
    Long create(BookDto bookDto);
    BookDto update(BookDto bookDto);
    void delete(Long id);
    BigDecimal getSumPriceByAuthor (String author);

}
