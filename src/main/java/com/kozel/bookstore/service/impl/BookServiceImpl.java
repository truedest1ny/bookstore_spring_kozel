package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.BookRepository;
import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.dto.book.BookDto;
import com.kozel.bookstore.service.dto.book.BookShowingDto;
import com.kozel.bookstore.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final DataMapper dataMapper;

    @Override
    public List<BookDto> getAll() {
        log.debug("Called getAll() method");

        return bookRepository.findAll()
                .stream()
                .map(dataMapper::toDto)
                .toList();
    }

    @Override
    public List<BookShowingDto> getBooksDtoShort() {
        log.debug("Called getBooksDtoShort() method");

        return bookRepository.findAll()
                .stream()
                .map(dataMapper::toShortedDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        log.debug("Called getById() method");
            Book book = bookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find book by id " + id)
            );

            return dataMapper.toDto(book);
    }

    @Override
    public BookDto create(BookDto bookDto) {
        log.debug("Called create() method");

        Book entity = dataMapper.toEntity(bookDto);
        Book savedBook = bookRepository.save(entity);
        return dataMapper.toDto(savedBook);
    }

    @Override
    public BookDto update(BookDto bookDto) {
        log.debug("Called update() method");

        Book entity = dataMapper.toEntity(bookDto);
        Book savedBook = bookRepository.save(entity);
        return dataMapper.toDto(savedBook);
    }

    @Override
    public void disable(Long id) {
        log.debug("Called disable() method");
                BookDto book = dataMapper.toDto(
                        bookRepository.findById(id).orElseThrow(
                                () -> new RuntimeException("Cannot find book (id = " + id + ")." +
                                        " There is nothing to delete. ")
                        ));
                bookRepository.delete(dataMapper.toEntity(book));
    }

    @Override
    public BigDecimal getSumPriceByAuthor(String author) {
        log.debug("Called getSumPriceByAuthor() method");

        BigDecimal SumPrice = BigDecimal.valueOf(0);
        List<BookDto> bookDtos = bookRepository.findByAuthor(author)
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

