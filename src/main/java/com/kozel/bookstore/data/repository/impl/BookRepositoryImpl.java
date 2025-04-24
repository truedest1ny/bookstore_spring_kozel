package com.kozel.bookstore.data.repository.impl;

import com.kozel.bookstore.data.dao.BookDao;
import com.kozel.bookstore.data.dto.BookDto;
import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final BookDao bookDao;
    private final DataMapper dataMapper;

    @Override
    public Book findByIsbn(String isbn) {
        return dataMapper.toEntity(bookDao.findByIsbn(isbn));
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookDao.findByAuthor(author)
                .stream()
                .map(dataMapper::toEntity)
                .toList();
    }

    @Override
    public long countAll() {
        return bookDao.countAll();
    }

    @Override
    public long clearDeletedRows() {
        return bookDao.clearDeletedRows();
    }

    @Override
    public Long save(Book book) {
        return bookDao.save(dataMapper.toDto(book));
    }

    @Override
    public Book findById(Long id) {
        return dataMapper.toEntity(bookDao.findById(id));
    }

    @Override
    public List<Book> findAll() {
        return bookDao.findAll()
                .stream()
                .map(dataMapper::toEntity)
                .toList();
    }

    @Override
    public Book update(Book book) {
        BookDto bookDto = dataMapper.toDto(book);
        BookDto savedBookDto = bookDao.update(bookDto);

        return dataMapper.toEntity(savedBookDto);
    }

    @Override
    public void delete(Book book) {
        bookDao.delete(dataMapper.toDto(book));
    }
}
