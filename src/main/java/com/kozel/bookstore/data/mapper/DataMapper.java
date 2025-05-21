package com.kozel.bookstore.data.mapper;

import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.entity.Order;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.service.dto.ServiceBookDto;
import com.kozel.bookstore.service.dto.ServiceBookShowingDto;
import com.kozel.bookstore.service.dto.ServiceOrderDto;
import com.kozel.bookstore.service.dto.ServiceOrderShowingDto;
import com.kozel.bookstore.service.dto.ServiceUserCreateDto;
import com.kozel.bookstore.service.dto.ServiceUserDto;
import com.kozel.bookstore.service.dto.ServiceUserShowingDto;

public interface DataMapper {
    ServiceBookDto toServiceDto(Book bookEntity);
    Book toEntity(ServiceBookDto serviceBookDto);
    ServiceUserDto toServiceDto(User userEntity);
    User toEntity (ServiceUserDto serviceUserDto);
    User toEntity (ServiceUserCreateDto serviceUserCreateDto);
    ServiceUserShowingDto toServiceShortedDto(User userEntity);
    ServiceBookShowingDto toServiceShortedDto(Book bookEntity);
    Order toEntity (ServiceOrderDto serviceOrderDto);
    ServiceOrderDto toServiceDto(Order orderEntity);
    ServiceOrderShowingDto toServiceShortedDto(Order orderEntity);
}
