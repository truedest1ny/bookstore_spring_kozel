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
import org.springframework.stereotype.Service;

@Service
public class DataMapperImpl implements DataMapper {
    @Override
    public UserDto toServiceDto(User userEntity) {
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
    public BookDto toServiceDto(Book bookEntity) {
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
    public UserShowingDto toServiceShortedDto(User userEntity) {

        UserShowingDto userShowingDto = new UserShowingDto();

        userShowingDto.setId(userEntity.getId());
        userShowingDto.setEmail(userEntity.getEmail());
        userShowingDto.setLogin(userEntity.getLogin());
        userShowingDto.setRole(UserShowingDto.Role.valueOf(userEntity.getRole().toString()));

        return userShowingDto;
    }

    @Override
    public BookShowingDto toServiceShortedDto(Book bookEntity) {
        BookShowingDto bookShowingDto = new BookShowingDto();
        bookShowingDto.setId(bookEntity.getId());
        bookShowingDto.setName(bookEntity.getName());
        bookShowingDto.setAuthor(bookEntity.getAuthor());
        bookShowingDto.setPublishedYear(bookEntity.getPublishedYear());
        return bookShowingDto;
    }

    @Override
    public Order toEntity(OrderDto orderDto) {
        Order entity = new Order();

        entity.setId(orderDto.getId());
        entity.setDate(orderDto.getDate());
        entity.setUser(orderDto.getUser());
        entity.setItems(orderDto.getItems());
        entity.setStatus(Order.Status.valueOf(orderDto.getStatus().toString()));
        entity.setTotalPrice(orderDto.getTotalPrice());

        return entity;
    }

    @Override
    public OrderDto toServiceDto(Order orderEntity) {
        OrderDto dto = new OrderDto();

        dto.setId(orderEntity.getId());
        dto.setDate(orderEntity.getDate());
        dto.setUser(orderEntity.getUser());
        dto.setItems(orderEntity.getItems());
        dto.setStatus(OrderDto.Status.valueOf(orderEntity.getStatus().toString()));
        dto.setTotalPrice(orderEntity.getTotalPrice());

        return dto;
    }

    @Override
    public OrderShowingDto toServiceShortedDto(Order orderEntity) {
        OrderShowingDto dto = new OrderShowingDto();

        dto.setId(orderEntity.getId());
        dto.setDate(orderEntity.getDate());
        dto.setUserLogin(orderEntity.getUser().getLogin());
        dto.setStatus(OrderShowingDto.Status.valueOf(orderEntity.getStatus().toString()));
        dto.setTotalPrice(orderEntity.getTotalPrice());

        return dto;
    }
}
