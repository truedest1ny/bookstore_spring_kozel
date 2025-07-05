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
import com.kozel.bookstore.service.dto.UserSessionDto;
import com.kozel.bookstore.service.dto.UserShowingDto;
import com.kozel.bookstore.service.dto.UserUpdateDto;

public interface DataMapper {
    BookDto toDto(Book bookEntity);
    Book toEntity(BookDto bookDto);
    UserDto toDto(User userEntity);
    User toEntity (UserDto userDto);
    User toEntity (UserCreateDto userCreateDto);
    UserShowingDto toShortedDto(User userEntity);
    UserSessionDto toSessionDto(UserDto userDto);
    BookShowingDto toShortedDto(Book bookEntity);
    Order toEntity (OrderDto orderDto);
    OrderDto toDto(Order orderEntity);
    OrderShowingDto toShortedDto(Order orderEntity);
    void mapToEntity(UserUpdateDto dto, User user);
}
