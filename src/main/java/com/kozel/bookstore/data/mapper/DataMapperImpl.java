package com.kozel.bookstore.data.mapper;

import com.kozel.bookstore.data.dto.BookDto;
import com.kozel.bookstore.data.dto.UserDto;
import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.entity.Order;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.service.dto.*;
import org.springframework.stereotype.Service;

@Service
public class DataMapperImpl implements DataMapper {
    @Override
    public ServiceUserDto toServiceDto(User userEntity) {
        ServiceUserDto serviceUserDto = new ServiceUserDto();

        serviceUserDto.setId(userEntity.getId());
        serviceUserDto.setFirstName(userEntity.getFirstName());
        serviceUserDto.setLastName(userEntity.getLastName());
        serviceUserDto.setEmail(userEntity.getEmail());
        serviceUserDto.setLogin(userEntity.getLogin());
        serviceUserDto.setPassword(userEntity.getPassword());
        serviceUserDto.setRole(ServiceUserDto.Role.valueOf(userEntity.getRole().toString()));
        serviceUserDto.setDeleted(userEntity.isDeleted());

        return serviceUserDto;
    }

    @Override
    public User toEntity(ServiceUserDto serviceUserDto) {
        User user = new User();

        user.setId(serviceUserDto.getId());
        user.setFirstName(serviceUserDto.getFirstName());
        user.setLastName(serviceUserDto.getLastName());
        user.setEmail(serviceUserDto.getEmail());
        user.setLogin(serviceUserDto.getLogin());
        user.setPassword(serviceUserDto.getPassword());
        user.setRole(User.Role.valueOf(serviceUserDto.getRole().toString()));
        user.setDeleted(serviceUserDto.isDeleted());

        return user;

    }

    @Override
    public User toEntity(ServiceUserCreateDto serviceUserCreateDto) {

        User user = new User();

        user.setEmail(serviceUserCreateDto.getEmail());
        user.setLogin(serviceUserCreateDto.getLogin());
        user.setPassword(serviceUserCreateDto.getPassword());

        return user;
    }



    @Override
    public ServiceBookDto toServiceDto(Book bookEntity) {
        ServiceBookDto serviceBookDto = new ServiceBookDto();
        serviceBookDto.setId(bookEntity.getId());
        serviceBookDto.setName(bookEntity.getName());
        serviceBookDto.setIsbn(bookEntity.getIsbn());
        serviceBookDto.setCover(ServiceBookDto.Cover.valueOf(bookEntity.getCover().toString()));
        serviceBookDto.setAuthor(bookEntity.getAuthor());
        serviceBookDto.setPublishedYear(bookEntity.getPublishedYear());
        serviceBookDto.setPrice(bookEntity.getPrice());
        serviceBookDto.setDeleted(bookEntity.isDeleted());
        return serviceBookDto;
    }

    @Override
    public Book toEntity(ServiceBookDto serviceBookDto) {
        Book book = new Book();
        book.setId(serviceBookDto.getId());
        book.setName(serviceBookDto.getName());
        book.setIsbn(serviceBookDto.getIsbn());
        book.setCover(Book.Cover.valueOf(serviceBookDto.getCover().toString()));
        book.setAuthor(serviceBookDto.getAuthor());
        book.setPublishedYear(serviceBookDto.getPublishedYear());
        book.setPrice(serviceBookDto.getPrice());
        book.setDeleted(serviceBookDto.isDeleted());
        return book;
    }

    @Override
    public ServiceUserShowingDto toServiceShortedDto(User userEntity) {

        ServiceUserShowingDto serviceUserShowingDto = new ServiceUserShowingDto();

        serviceUserShowingDto.setId(userEntity.getId());
        serviceUserShowingDto.setEmail(userEntity.getEmail());
        serviceUserShowingDto.setLogin(userEntity.getLogin());
        serviceUserShowingDto.setRole(ServiceUserShowingDto.Role.valueOf(userEntity.getRole().toString()));

        return serviceUserShowingDto;
    }

    @Override
    public ServiceBookShowingDto toServiceShortedDto(Book bookEntity) {
        ServiceBookShowingDto serviceBookShowingDto = new ServiceBookShowingDto();
        serviceBookShowingDto.setId(bookEntity.getId());
        serviceBookShowingDto.setName(bookEntity.getName());
        serviceBookShowingDto.setAuthor(bookEntity.getAuthor());
        serviceBookShowingDto.setPublishedYear(bookEntity.getPublishedYear());
        return serviceBookShowingDto;
    }

    @Override
    public Order toEntity(ServiceOrderDto serviceOrderDto) {
        Order entity = new Order();

        entity.setId(serviceOrderDto.getId());
        entity.setDate(serviceOrderDto.getDate());
        entity.setUser(serviceOrderDto.getUser());
        entity.setItems(serviceOrderDto.getItems());
        entity.setStatus(Order.Status.valueOf(serviceOrderDto.getStatus().toString()));
        entity.setTotalPrice(serviceOrderDto.getTotalPrice());

        return entity;
    }

    @Override
    public ServiceOrderDto toServiceDto(Order orderEntity) {
        ServiceOrderDto dto = new ServiceOrderDto();

        dto.setId(orderEntity.getId());
        dto.setDate(orderEntity.getDate());
        dto.setUser(orderEntity.getUser());
        dto.setItems(orderEntity.getItems());
        dto.setStatus(ServiceOrderDto.Status.valueOf(orderEntity.getStatus().toString()));
        dto.setTotalPrice(orderEntity.getTotalPrice());

        return dto;
    }

    @Override
    public ServiceOrderShowingDto toServiceShortedDto(Order orderEntity) {
        ServiceOrderShowingDto dto = new ServiceOrderShowingDto();

        dto.setId(orderEntity.getId());
        dto.setDate(orderEntity.getDate());
        dto.setUserLogin(orderEntity.getUser().getLogin());
        dto.setStatus(ServiceOrderShowingDto.Status.valueOf(orderEntity.getStatus().toString()));
        dto.setTotalPrice(orderEntity.getTotalPrice());

        return dto;
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
    public BookDto toDto(Book bookEntity) {
        BookDto bookDto = new BookDto();
        bookDto.setId(bookEntity.getId());
        bookDto.setName(bookEntity.getName());
        bookDto.setIsbn(bookEntity.getIsbn());
        bookDto.setCover(BookDto.Cover.valueOf(bookEntity.getCover().toString()));
        bookDto.setAuthor(bookEntity.getAuthor());
        bookDto.setPublishedYear(bookEntity.getPublishedYear());
        bookDto.setPrice(bookEntity.getPrice());
        bookDto.setDeleted(bookEntity.isDeleted());
        return bookDto;
    }

    @Override
    public UserDto toDto(User userEntity) {
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setLogin(userEntity.getLogin());
        userDto.setPassword(userEntity.getPassword());
        userDto.setRole(UserDto.Role.valueOf(userEntity.getRole().toString()));
        userDto.setDeleted(userEntity.isDeleted());

        return userDto;
    }
}
