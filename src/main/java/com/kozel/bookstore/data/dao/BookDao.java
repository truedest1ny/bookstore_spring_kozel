package com.kozel.bookstore.data.dao;

import com.kozel.bookstore.data.entity.Book;

import java.util.List;

public interface BookDao extends CrudDao<Long, Book>{

    Book findByIsbn(String isbn);
    List<Book> findByAuthor(String author);
    long countAll();
}
