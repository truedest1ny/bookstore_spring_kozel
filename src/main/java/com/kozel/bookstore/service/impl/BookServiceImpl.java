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

import java.math.BigDecimal;
import java.util.List;

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

        Book book = bookDao.findById(id);
        if (book == null)
        {
            throw new BookNotFoundException("Cannot find book by id " + id);
        }
        return dataMapper.toDto(book);
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
    public void delete(Long id) {

        log.debug("Called delete() method");

        boolean success = bookDao.deleteById(id);
        if (!success)
        {
           throw new RuntimeException("Cannot delete book (id = " + id + ")");
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
