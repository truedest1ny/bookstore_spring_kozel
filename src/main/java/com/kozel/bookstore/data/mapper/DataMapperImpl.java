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
}
