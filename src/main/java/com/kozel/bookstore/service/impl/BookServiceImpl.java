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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final DataMapper mapper;

    @Override
    public List<BookDto> getAll() {
        log.debug("Called getAll() method");

        return mapper.toBookDtoList(
                bookRepository.findAll());
    }

    @Override
    public Page<BookShowingDto> getBooksDtoShort(Pageable pageable) {
        log.debug("Called getBooksDtoShort() method");
        return bookRepository.findAll(pageable)
                .map(mapper::toShortedDto);
    }

    @Override
    public BookDto getById(Long id) {
        log.debug("Called getById() method");
            Book book = bookRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find book by id " + id)
            );
            return mapper.toDto(book);
    }

    @Override
    public BookDto create(BookDto bookDto) {
        log.debug("Called create() method");

        Book createtBook = mapper.toEntity(bookDto);
        Book savedBook = bookRepository.save(createtBook);
        return mapper.toDto(savedBook);
    }

    @Override
    public BookDto update(BookDto bookDto) {
        log.debug("Called update() method");

        if (bookDto.getId() == null) {
            throw new IllegalArgumentException(
                    "Book ID must be provided for update operation.");
        }

        Book existingBook = bookRepository.findById(bookDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Book not found for update with ID: " + bookDto.getId()));

        mapper.updateEntityFromDto(bookDto, existingBook);
        Book updatedBook = bookRepository.save(existingBook);
        return mapper.toDto(updatedBook);
    }

    @Override
    public void disable(Long id) {
        log.debug("Called disable() method");

        if (!bookRepository.existsById(id)){
           throw  new RuntimeException("Cannot find book (id = " + id + ")." +
                    " There is nothing to delete. ");
        }
        bookRepository.softDelete(id);
    }

    @Override
    public BigDecimal getSumPriceByAuthor(String author) {
        log.debug("Called getSumPriceByAuthor() method");

        BigDecimal SumPrice = BigDecimal.valueOf(0);
        List<BookDto> bookDtos = bookRepository.findByAuthor(author)
                .stream()
                .map(mapper::toDto)
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

