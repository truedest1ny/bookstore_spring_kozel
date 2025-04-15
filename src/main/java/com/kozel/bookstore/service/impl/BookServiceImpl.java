package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.dao.BookDao;
import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.dto.BookDto;
import com.kozel.bookstore.service.dto.BookDtoShowing;
import com.kozel.bookstore.service.exception.BookNotFoundException;
import com.kozel.bookstore.service.mapper.DataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final DataMapper dataMapper;

    @Override
    public List<BookDto> getAll() {
        log.debug("Called getAll() method");

        return bookDao.findAll()
                .stream()
                .map(dataMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoShowing> getBooksDtoShort() {
        log.debug("Called getBooksDtoShort() method");

        return bookDao.findAll()
                .stream()
                .map(dataMapper::toDtoShorted)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        log.debug("Called getById() method");
        try {

            Book book = bookDao.findById(id);

            return dataMapper.toDto(book);
        } catch (DataAccessException e) {
            throw new BookNotFoundException("Cannot find book by id " + id);
        }

    }

    @Override
    public Long create(BookDto bookDto) {
        log.debug("Called create() method");

        Book entity = dataMapper.toEntity(bookDto);
        return bookDao.save(entity);
    }

    @Override
    public BookDto update(BookDto bookDto) {
        log.debug("Called update() method");

        Book entity = dataMapper.toEntity(bookDto);
        Book savedBook = bookDao.update(entity);
        return dataMapper.toDto(savedBook);
    }

    @Override
    public void disable(Long id) {
        log.debug("Called disable() method");

        try {
                BookDto book = dataMapper.toDto(bookDao.findById(id));
                book.setDeleted(true);
                bookDao.delete(dataMapper.toEntity(book));

        } catch (DataAccessException e){
            throw new RuntimeException("Cannot delete book (id = " + id + "): " + e.getMessage());
        }
    }

    @Override
    public BigDecimal getSumPriceByAuthor(String author) {
        log.debug("Called getSumPriceByAuthor() method");

        BigDecimal SumPrice = BigDecimal.valueOf(0);
        List<BookDto> bookDtos = bookDao.findByAuthor(author)
                .stream()
                .map(dataMapper::toDto)
                .toList();
        if (bookDtos.isEmpty())
        {
            return SumPrice;
        }

        for (BookDto bookDto : bookDtos) {
            SumPrice = SumPrice.add(bookDto.getPrice());
        }
        return SumPrice;
    }
}

