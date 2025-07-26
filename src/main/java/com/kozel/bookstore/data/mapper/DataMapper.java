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
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DataMapper {

    UserDto toDto(User userEntity);
    List<UserDto> toUserDtoList(List<User> userEntities);

    @Mapping(target = "hash", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hash", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "role", constant = "CUSTOMER")
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    User toEntity (UserCreateDto userCreateDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hash", ignore = true)
    @Mapping(target = "login", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void mapToEntity(UserUpdateDto dto,@MappingTarget User user);

    UserShowingDto toShortedDto(User userEntity);
    List<UserShowingDto> toUserShowingDtoList(List<User> userEntities);
    UserSessionDto toSessionDto(UserDto userDto);

    BookDto toDto(Book bookEntity);
    List<BookDto> toBookDtoList(List<Book> bookEntities);

    @Mapping(target = "deleted", ignore = true)
    Book toEntity(BookDto bookDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isbn", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateEntityFromDto(BookDto bookDto, @MappingTarget Book bookEntity);

    BookShowingDto toShortedDto(Book bookEntity);
    List<BookShowingDto> toBookShowingDtoList(List<Book> bookEntities);

    OrderDto toDto(Order entity);
    List<OrderDto> toOrderDtoList(List<Order> entities);

    @Mapping(target = "user", source = "dto.user")
    @Mapping(target = "items", source = "dto.items")
    @Mapping(target = "totalPrice", ignore = true)
    Order toEntity (OrderDto dto);

    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemDto orderItemDto);

    @Mapping(target = "userLogin", source = "entity.user.login")
    OrderShowingDto toShortedDto(Order entity);

    List<OrderShowingDto> toOrderShowingDtoList(List<Order> entities);

    @Mapping(target = "userId", expression =
            "java(entity.getUser() != null ? entity.getUser().getId() : null)")
    CartDto toDto(Cart entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    Cart toEntity(CartDto dto);

    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "book", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "price", ignore = true)
    CartItem toEntity(CartItemDto cartItemDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toOrderItem (CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "user", source = "cart.user")
    @Mapping(target = "items", source = "cart.items")
    Order toOrder (Cart cart);

}
