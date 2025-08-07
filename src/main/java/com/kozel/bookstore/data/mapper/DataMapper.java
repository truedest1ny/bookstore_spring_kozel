package com.kozel.bookstore.data.mapper;

import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.entity.Cart;
import com.kozel.bookstore.data.entity.CartItem;
import com.kozel.bookstore.data.entity.Order;
import com.kozel.bookstore.data.entity.OrderItem;
import com.kozel.bookstore.data.entity.OrderedBook;
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
import org.mapstruct.Named;

import java.time.format.DateTimeFormatter;
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

    @Named("toDto")
    @Mapping(target = "formattedDate", ignore = true)
    OrderDto toDto(Order entity);
    List<OrderDto> toOrderDtoList(List<Order> entities);

    OrderItemDto toDto(OrderItem orderItem);

    @Named("toShortedDto")
    @Mapping(target = "formattedDate", ignore = true)
    @Mapping(target = "userLogin", source = "entity.user.login")
    OrderShowingDto toShortedDto(Order entity);

    List<OrderShowingDto> toOrderShowingDtoList(List<Order> entities);

    @Mapping(target = "userId", expression =
            "java(entity.getUser() != null ? entity.getUser().getId() : null)")
    CartDto toDto(Cart entity);

    CartItemDto toDto(CartItem cartItem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "book", source = "cartItem.book")
    @Mapping(target = "price", ignore = true)
    OrderItem toOrderItem (CartItem cartItem);

    @Mapping(target = "orderItemId", ignore = true)
    @Mapping(target = "orderItem", ignore = true)
    @Mapping(target = "originalBookId", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "publishedYear", source = "publishedYear")
    @Mapping(target = "priceAtOrder", source = "price")
    OrderedBook toOrderedBook(Book book);

    @Mapping(target = "id", source = "originalBookId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "publishedYear", source = "publishedYear")
    @Mapping(target = "price", source = "priceAtOrder")
    @Mapping(target = "cover", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    BookDto toBookDto(OrderedBook orderedBook);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "user", source = "cart.user")
    @Mapping(target = "items", source = "cart.items")
    Order toOrder (Cart cart);

    default OrderDto mapOrderToDtoWithDate(Order entity) {
        OrderDto dto = toDto(entity);

        if (dto.getDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            dto.setFormattedDate(dto.getDate().format(formatter));
        }

        return dto;
    }

    default OrderShowingDto mapOrderToShortedDtoWithDate(Order entity) {
        OrderShowingDto dto = toShortedDto(entity);

        if (dto.getDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            dto.setFormattedDate(dto.getDate().format(formatter));
        }
        return dto;
    }

}
