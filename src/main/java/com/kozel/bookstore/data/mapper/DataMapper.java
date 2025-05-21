package com.kozel.bookstore.data.mapper;

import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.entity.Order;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.service.dto.BookDto;
import com.kozel.bookstore.service.dto.BookShowingDto;
import com.kozel.bookstore.service.dto.OrderDto;
import com.kozel.bookstore.service.dto.OrderShowingDto;
import com.kozel.bookstore.service.dto.UserCreateDto;
import com.kozel.bookstore.service.dto.UserDto;
import com.kozel.bookstore.service.dto.UserShowingDto;

public interface DataMapper {
    BookDto toServiceDto(Book bookEntity);
    Book toEntity(BookDto bookDto);
    UserDto toServiceDto(User userEntity);
    User toEntity (UserDto userDto);
    User toEntity (UserCreateDto userCreateDto);
    UserShowingDto toServiceShortedDto(User userEntity);
    BookShowingDto toServiceShortedDto(Book bookEntity);
    Order toEntity (OrderDto orderDto);
    OrderDto toServiceDto(Order orderEntity);
    OrderShowingDto toServiceShortedDto(Order orderEntity);
}
