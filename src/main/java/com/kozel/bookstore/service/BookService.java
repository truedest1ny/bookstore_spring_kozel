package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.ServiceBookDto;
import com.kozel.bookstore.service.dto.ServiceBookShowingDto;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    List<ServiceBookDto> getAll();
    List<ServiceBookShowingDto> getBooksDtoShort();
    ServiceBookDto getById(Long id);
    Long create(ServiceBookDto serviceBookDto);
    ServiceBookDto update(ServiceBookDto serviceBookDto);
    void disable(Long id);
    BigDecimal getSumPriceByAuthor (String author);

}
