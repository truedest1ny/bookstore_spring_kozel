package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.data.entity.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Long, Book> {
    Book findByIsbn(String isbn);
    List<Book> findByAuthor(String author);
    long countAll();
    long clearDeletedRows();
}
