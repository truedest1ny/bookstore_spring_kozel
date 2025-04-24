package com.kozel.bookstore.data.dao;

import com.kozel.bookstore.data.dto.BookDto;

import java.util.List;

public interface BookDao extends CrudDao<Long, BookDto>{
    BookDto findByIsbn(String isbn);
    List<BookDto> findByAuthor(String author);
    long countAll();
    long clearDeletedRows();

}
