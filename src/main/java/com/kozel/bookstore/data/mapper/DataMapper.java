package com.kozel.bookstore.data.mapper;

import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.entity.Cart;
import com.kozel.bookstore.data.entity.CartItem;
import com.kozel.bookstore.data.entity.Order;
import com.kozel.bookstore.data.entity.OrderItem;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.service.dto.book.BookDto;
import com.kozel.bookstore.service.dto.book.BookShowingDto;
import com.kozel.bookstore.service.dto.cart.CartDto;
import com.kozel.bookstore.service.dto.cart.CartItemDto;
import com.kozel.bookstore.service.dto.order.OrderDto;
import com.kozel.bookstore.service.dto.order.OrderItemDto;
import com.kozel.bookstore.service.dto.order.OrderShowingDto;
import com.kozel.bookstore.service.dto.user.UserCreateDto;
import com.kozel.bookstore.service.dto.user.UserDto;
import com.kozel.bookstore.service.dto.user.UserSessionDto;
import com.kozel.bookstore.service.dto.user.UserShowingDto;
import com.kozel.bookstore.service.dto.user.UserUpdateDto;

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
    OrderItemDto toDto(OrderItem orderItem);
    OrderItem toEntity(OrderItemDto orderItemDto);
    CartDto toDto(Cart cart);
    CartDto toDto(Cart entity, Long userId);
    Cart toEntity(CartDto cartDto);
    CartItem toEntity(CartItemDto cartItemDto);
    CartItemDto toDto(CartItem cartItem);
    OrderShowingDto toShortedDto(Order orderEntity);
    OrderItem toOrderItem (CartItem cartItem);
    Order toOrder (Cart cart);
    void mapToEntity(UserUpdateDto dto, User user);
}
