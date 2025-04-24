package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.BookRepository;
import com.kozel.bookstore.service.BookService;
import com.kozel.bookstore.service.dto.ServiceBookDto;
import com.kozel.bookstore.service.dto.ServiceBookShowingDto;
import com.kozel.bookstore.service.exception.BookNotFoundException;
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

    private final BookRepository bookRepository;
    private final DataMapper dataMapper;

    @Override
    public List<ServiceBookDto> getAll() {
        log.debug("Called getAll() method");

        return bookRepository.findAll()
                .stream()
                .map(dataMapper::toServiceDto)
                .toList();
    }

    @Override
    public List<ServiceBookShowingDto> getBooksDtoShort() {
        log.debug("Called getBooksDtoShort() method");

        return bookRepository.findAll()
                .stream()
                .map(dataMapper::toServiceShortedDto)
                .toList();
    }

    @Override
    public ServiceBookDto getById(Long id) {
        log.debug("Called getById() method");
        try {
            Book book = bookRepository.findById(id);

            return dataMapper.toServiceDto(book);
        } catch (DataAccessException e) {
            throw new BookNotFoundException("Cannot find book by id " + id);
        }

    }

    @Override
    public Long create(ServiceBookDto serviceBookDto) {
        log.debug("Called create() method");

        Book entity = dataMapper.toEntity(serviceBookDto);
        return bookRepository.save(entity);
    }

    @Override
    public ServiceBookDto update(ServiceBookDto serviceBookDto) {
        log.debug("Called update() method");

        Book entity = dataMapper.toEntity(serviceBookDto);
        Book savedBook = bookRepository.update(entity);
        return dataMapper.toServiceDto(savedBook);
    }

    @Override
    public void disable(Long id) {
        log.debug("Called disable() method");
        try {
                ServiceBookDto book = dataMapper.toServiceDto(bookRepository.findById(id));
                book.setDeleted(true);
                bookRepository.delete(dataMapper.toEntity(book));

        } catch (DataAccessException e){
            throw new RuntimeException("Cannot delete book (id = " + id + "): " + e.getMessage());
        }
    }

    @Override
    public BigDecimal getSumPriceByAuthor(String author) {
        log.debug("Called getSumPriceByAuthor() method");

        BigDecimal SumPrice = BigDecimal.valueOf(0);
        List<ServiceBookDto> serviceBookDtos = bookRepository.findByAuthor(author)
                .stream()
                .map(dataMapper::toServiceDto)
                .toList();
        if (serviceBookDtos.isEmpty())
        {
            return SumPrice;
        }
        for (ServiceBookDto serviceBookDto : serviceBookDtos) {
            SumPrice = SumPrice.add(serviceBookDto.getPrice());
        }
        return SumPrice;
    }
}

