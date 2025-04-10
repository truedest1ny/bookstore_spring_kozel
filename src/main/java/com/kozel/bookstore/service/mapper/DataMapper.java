package com.kozel.bookstore.service.mapper;

import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.service.dto.*;

public interface DataMapper {
    BookDto toDto(Book entity);
    Book toEntity(BookDto dto);
    UserDto toDto (User entity);
    User toEntity (UserDto dto);
    User toEntity (UserCreateDto userCreateDto);
    UserDtoShowing toDtoShorted (User entity);
    BookDtoShowing toDtoShorted(Book entity);
}
