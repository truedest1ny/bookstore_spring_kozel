package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.data.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Long, Book> {
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByAuthor(String author);
    long countAll();
    void clearDeletedRows();
}
