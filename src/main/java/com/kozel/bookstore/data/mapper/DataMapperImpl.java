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
import org.springframework.stereotype.Service;

@Service
public class DataMapperImpl implements DataMapper {
    @Override
    public UserDto toDto(User userEntity) {
        UserDto userDto = new UserDto();

        userDto.setId(userEntity.getId());
        userDto.setFirstName(userEntity.getFirstName());
        userDto.setLastName(userEntity.getLastName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setLogin(userEntity.getLogin());
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
        user.setRole(User.Role.valueOf(userDto.getRole().toString()));
        user.setDeleted(userDto.isDeleted());

        return user;
    }

    @Override
    public User toEntity(UserCreateDto userCreateDto) {

        User user = new User();

        user.setEmail(userCreateDto.getEmail());
        user.setLogin(userCreateDto.getLogin());

        return user;
    }

    @Override
    public UserSessionDto toSessionDto(UserDto userDto) {
        UserSessionDto sessionDto = new UserSessionDto();
        sessionDto.setId(userDto.getId());
        sessionDto.setLogin(userDto.getLogin());
        sessionDto.setRole(UserSessionDto.Role.valueOf(userDto.getRole().toString()));
        return sessionDto;
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
    public UserShowingDto toShortedDto(User userEntity) {

        UserShowingDto userShowingDto = new UserShowingDto();

        userShowingDto.setId(userEntity.getId());
        userShowingDto.setEmail(userEntity.getEmail());
        userShowingDto.setLogin(userEntity.getLogin());
        userShowingDto.setRole(UserShowingDto.Role.valueOf(userEntity.getRole().toString()));

        return userShowingDto;
    }

    @Override
    public BookShowingDto toShortedDto(Book bookEntity) {
        BookShowingDto bookShowingDto = new BookShowingDto();
        bookShowingDto.setId(bookEntity.getId());
        bookShowingDto.setName(bookEntity.getName());
        bookShowingDto.setAuthor(bookEntity.getAuthor());
        bookShowingDto.setPublishedYear(bookEntity.getPublishedYear());
        bookShowingDto.setPrice(bookEntity.getPrice());
        return bookShowingDto;
    }

    @Override
    public Order toEntity(OrderDto dto) {
        Order entity = new Order();

        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        if (dto.getUser() != null) {
            entity.setUser(toEntity(dto.getUser()));
        }
        if (dto.getItems() != null) {
                dto.getItems().forEach(itemDto -> {
                    OrderItem item = toEntity(itemDto);
                    entity.addItem(item);});
        }
        entity.setStatus(Order.Status.valueOf(dto.getStatus().toString()));


        return entity;
    }

    @Override
    public OrderDto toDto(Order entity) {
        OrderDto dto = new OrderDto();

        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        if (entity.getUser() != null) {
            dto.setUser(toDto(entity.getUser()));
        }
        if (entity.getItems() != null) {
                entity.getItems().forEach(item -> {
                    OrderItemDto itemDto = toDto(item);
                    dto.addItem(itemDto);});
        }
        dto.setStatus(OrderDto.Status.valueOf(entity.getStatus().toString()));
        dto.setTotalPrice(entity.getTotalPrice());

        return dto;
    }

    @Override
    public OrderItemDto toDto(OrderItem entity) {
        OrderItemDto dto = new OrderItemDto();
        dto.setId(entity.getId());
        dto.setBook(toDto(entity.getBook()));
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    @Override
    public OrderItem toEntity(OrderItemDto dto) {
        OrderItem entity = new OrderItem();
        entity.setId(dto.getId());
        entity.setBook(toEntity(dto.getBook()));
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    @Override
    public CartDto toDto(Cart entity, Long userId) {
        CartDto dto = new CartDto();
        dto.setId(entity.getId());
        dto.setUserId(userId);
        if (entity.getItems() != null) {
            entity.getItems().forEach(item -> {
                CartItemDto itemDto = toDto(item);
                dto.addItem(itemDto);});
        }
        dto.setTotalPrice(entity.getTotalPrice());
        return dto;
    }

    @Override
    public CartDto toDto(Cart entity) {
        return toDto(entity,
                entity.getUser() != null ? entity.getUser().getId() : null);
    }

    @Override
    public Cart toEntity(CartDto dto) {
        Cart entity = new Cart();
        if (dto.getItems() != null) {
            dto.getItems().forEach(itemDto -> {
                CartItem item = toEntity(itemDto);
                entity.addItem(item);});
        }
        entity.setTotalPrice(dto.getTotalPrice());
        return entity;
    }



    @Override
    public CartItem toEntity(CartItemDto dto) {
        CartItem entity = new CartItem();
        entity.setId(dto.getId());
        entity.setBook(toEntity(dto.getBook()));
        entity.setQuantity(dto.getQuantity());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    @Override
    public CartItemDto toDto(CartItem entity) {
        CartItemDto dto = new CartItemDto();
        dto.setId(entity.getId());
        dto.setBook(toDto(entity.getBook()));
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    @Override
    public OrderShowingDto toShortedDto(Order entity) {
        OrderShowingDto dto = new OrderShowingDto();

        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setUserLogin(entity.getUser().getLogin());
        dto.setStatus(OrderShowingDto.Status.valueOf(entity.getStatus().toString()));
        dto.setTotalPrice(entity.getTotalPrice());

        return dto;
    }

    @Override
    public OrderItem toOrderItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getPrice());
        return orderItem;
    }

    @Override
    public Order toOrder(Cart cart) {
        Order order = new Order();
        if (cart.getUser() != null) {
            order.setUser(cart.getUser());
        }
        if (cart.getItems() != null) {
                cart.getItems().forEach(cartItem -> {
                    OrderItem orderItem = toOrderItem(cartItem);
                    order.addItem(orderItem);});
        }
        return order;
    }

    public void mapToEntity(UserUpdateDto dto, User user) {
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(User.Role.valueOf(dto.getRole().toString()));
    }
}
