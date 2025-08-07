package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.book.BookDto;
import com.kozel.bookstore.service.dto.book.BookShowingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    List<BookDto> getAll();
    Page<BookShowingDto> getBooksDtoShort(Pageable pageable);
    BookDto getById(Long id);
    BookDto create(BookDto bookDto);
    BookDto update(BookDto bookDto);
    void disable(Long id);
    BigDecimal getSumPriceByAuthor (String author);

}
