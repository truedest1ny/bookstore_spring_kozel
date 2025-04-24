package com.kozel.bookstore.data.mapper;

import com.kozel.bookstore.data.dto.BookDto;
import com.kozel.bookstore.data.dto.UserDto;
import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.entity.Order;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.service.dto.*;

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
    Book toEntity(BookDto bookDto);
    BookDto toDto(Book bookEntity);
    User toEntity(UserDto userDto);
    UserDto toDto(User userEntity);

}
