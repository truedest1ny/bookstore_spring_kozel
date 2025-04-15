package com.kozel.bookstore.service.mapper;

import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.service.dto.*;
import org.springframework.stereotype.Service;

@Service
public class DataMapperImpl implements DataMapper {
    @Override
    public UserDto toDto(User entity) {
       UserDto userDto = new UserDto();

        userDto.setId(entity.getId());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setEmail(entity.getEmail());
        userDto.setLogin(entity.getLogin());
        userDto.setPassword(entity.getPassword());
        userDto.setRole(UserDto.Role.valueOf(entity.getRole().toString()));
        userDto.setDeleted(entity.isDeleted());

        return userDto;
    }

    @Override
    public User toEntity(UserDto userDto) {

       User user = new User();

        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setRole(User.Role.valueOf(userDto.getRole().toString()));
        user.setDeleted(userDto.isDeleted());

        return user;

    }

    @Override
    public User toEntity(UserCreateDto userCreateDto) {

        User user = new User();

        user.setEmail(userCreateDto.getEmail());
        user.setLogin(userCreateDto.getLogin());
        user.setPassword(userCreateDto.getPassword());

        return user;
    }



    @Override
    public BookDto toDto(Book entity) {
        BookDto bookDto = new BookDto();
        bookDto.setId(entity.getId());
        bookDto.setName(entity.getName());
        bookDto.setIsbn(entity.getIsbn());
        bookDto.setCover(BookDto.Cover.valueOf(entity.getCover().toString()));
        bookDto.setAuthor(entity.getAuthor());
        bookDto.setPublishedYear(entity.getPublishedYear());
        bookDto.setPrice(entity.getPrice());
        bookDto.setDeleted(entity.isDeleted());
        return bookDto;
    }

    @Override
    public Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setName(bookDto.getName());
        book.setIsbn(bookDto.getIsbn());
        book.setCover(Book.Cover.valueOf(bookDto.getCover().toString()));
        book.setAuthor(bookDto.getAuthor());
        book.setPublishedYear(bookDto.getPublishedYear());
        book.setPrice(bookDto.getPrice());
        book.setDeleted(bookDto.isDeleted());
        return book;
    }

    @Override
    public UserDtoShowing toDtoShorted(User entity) {

        UserDtoShowing userDtoShowing = new UserDtoShowing();

        userDtoShowing.setId(entity.getId());
        userDtoShowing.setEmail(entity.getEmail());
        userDtoShowing.setLogin(entity.getLogin());
        userDtoShowing.setRole(UserDtoShowing.Role.valueOf(entity.getRole().toString()));

        return userDtoShowing;
    }

    @Override
    public BookDtoShowing toDtoShorted(Book entity) {
        BookDtoShowing bookDtoShowing = new BookDtoShowing();
        bookDtoShowing.setId(entity.getId());
        bookDtoShowing.setName(entity.getName());
        bookDtoShowing.setAuthor(entity.getAuthor());
        bookDtoShowing.setPublishedYear(entity.getPublishedYear());
        return bookDtoShowing;
    }
}
