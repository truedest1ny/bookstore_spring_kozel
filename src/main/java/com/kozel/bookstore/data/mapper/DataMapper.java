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
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * A central MapStruct mapper interface for converting data between entities and DTOs
 * across various domains of the application, including users, books, carts, and orders.
 * This mapper defines comprehensive rules for creating, reading, and updating data,
 * handling complex relationships and ensuring data integrity during conversion.
 * It is configured to be a Spring component, making it injectable throughout the application.
 *
 */
@Mapper(componentModel = "spring")
public interface DataMapper {

    /**
     * Converts a {@link User} entity to a detailed {@link UserDto}.
     * This is typically used for administrative purposes or profile viewing.
     * @param userEntity The source user entity.
     * @return A detailed user DTO.
     */
    UserDto toDto(User userEntity);

    /**
     * Converts a list of {@link User} entities to a list of detailed {@link UserDto}.
     * @param userEntities The list of user entities.
     * @return The list of user DTOs.
     */
    List<UserDto> toUserDtoList(List<User> userEntities);

    /**
     * Converts a detailed {@link UserDto} to a new {@link User} entity.
     * The `id` and `hash` fields are ignored as they are handled separately.
     * @param userDto The source user DTO.
     * @return A new user entity.
     */
    @Mapping(target = "hash", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto userDto);

    /**
     * Converts a {@link UserCreateDto} to a new {@link User} entity.
     * The `id` and `hash` are ignored. The `role` is hardcoded to "CUSTOMER"
     * for new user registration. Other fields are also ignored to ensure
     * a clean new entity creation.
     * @param userCreateDto The source DTO for creating a new user.
     * @return A new user entity with a "CUSTOMER" role.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hash", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "role", constant = "CUSTOMER")
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    User toEntity (UserCreateDto userCreateDto);

    /**
     * Updates an existing {@link User} entity with data from a {@link UserUpdateDto}.
     * The `id`, `hash`, `login`, and `isDeleted` fields are ignored to prevent
     * them from being modified during an update operation.
     * @param dto The source DTO with update data.
     * @param user The target user entity to be updated.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hash", ignore = true)
    @Mapping(target = "login", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void mapToEntity(UserUpdateDto dto,@MappingTarget User user);

    /**
     * Converts a {@link User} entity to a shortened {@link UserShowingDto}.
     * This DTO contains a minimal set of information for public display.
     * @param userEntity The source user entity.
     * @return A shortened user DTO.
     */
    UserShowingDto toShortedDto(User userEntity);

    /**
     * Converts a list of {@link User} entities to a list of shortened {@link UserShowingDto}.
     * @param userEntities The list of user entities.
     * @return The list of shortened user DTOs.
     */
    List<UserShowingDto> toUserShowingDtoList(List<User> userEntities);

    /**
     * Converts a {@link UserDto} to a {@link UserSessionDto} for storing in the session.
     * @param userDto The source user DTO.
     * @return A user session DTO.
     */
    UserSessionDto toSessionDto(UserDto userDto);

    /**
     * Converts a {@link Book} entity to a detailed {@link BookDto}.
     * @param bookEntity The source book entity.
     * @return A detailed book DTO.
     */
    BookDto toDto(Book bookEntity);

    /**
     * Converts a list of {@link Book} entities to a list of detailed {@link BookDto}.
     * @param bookEntities The list of book entities.
     * @return The list of book DTOs.
     */
    List<BookDto> toBookDtoList(List<Book> bookEntities);

    /**
     * Converts a {@link BookDto} to a new {@link Book} entity.
     * The `isDeleted` flag is ignored as it is handled by the soft-delete filter.
     * @param bookDto The source book DTO.
     * @return A new book entity.
     */
    @Mapping(target = "deleted", ignore = true)
    Book toEntity(BookDto bookDto);

    /**
     * Updates an existing {@link Book} entity with data from a {@link BookDto}.
     * The `id`, `isbn`, and `isDeleted` fields are ignored to prevent modification.
     * @param bookDto The source DTO with update data.
     * @param bookEntity The target book entity to be updated.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isbn", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateEntityFromDto(BookDto bookDto, @MappingTarget Book bookEntity);

    /**
     * Converts a {@link Book} entity to a shortened {@link BookShowingDto}.
     * This DTO contains a minimal set of information for public display.
     * @param bookEntity The source book entity.
     * @return A shortened book DTO.
     */
    BookShowingDto toShortedDto(Book bookEntity);

    /**
     * Converts a list of {@link Book} entities to a list of shortened {@link BookShowingDto}.
     * @param bookEntities The list of book entities.
     * @return The list of shortened book DTOs.
     */
    List<BookShowingDto> toBookShowingDtoList(List<Book> bookEntities);

    /**
     * Converts an {@link Order} entity to a detailed {@link OrderDto}.
     * This method is named "toDto" for internal use and can be called by other
     * methods via its {@code @Named} annotation. The `formattedDate` is ignored
     * here and should be handled by a subsequent method.
     * @param entity The source order entity.
     * @return A detailed order DTO.
     */
    @Named("toDto")
    @Mapping(target = "formattedDate", ignore = true)
    OrderDto toDto(Order entity);

    /**
     * Converts a list of {@link Order} entities to a list of detailed {@link OrderDto}.
     * @param entities The list of order entities.
     * @return The list of order DTOs.
     */
    List<OrderDto> toOrderDtoList(List<Order> entities);

    /**
     * Converts an {@link OrderItem} entity to an {@link OrderItemDto}.
     * @param orderItem The source order item entity.
     * @return An order item DTO.
     */
    OrderItemDto toDto(OrderItem orderItem);

    /**
     * Converts an {@link Order} entity to a shortened {@link OrderShowingDto}.
     * This method is named "toShortedDto" for internal use and is used for concise display.
     * It maps the user's login directly to the DTO.
     * @param entity The source order entity.
     * @return A shortened order DTO.
     */
    @Named("toShortedDto")
    @Mapping(target = "formattedDate", ignore = true)
    @Mapping(target = "userLogin", source = "entity.user.login")
    OrderShowingDto toShortedDto(Order entity);

    /**
     * Converts a list of {@link Order} entities to a list of shortened {@link OrderShowingDto}.
     * @param entities The list of order entities.
     * @return The list of shortened order DTOs.
     */
    List<OrderShowingDto> toOrderShowingDtoList(List<Order> entities);

    /**
     * Converts a {@link Cart} entity to a {@link CartDto}.
     * The {@code userId} is mapped from the user's ID within the cart.
     * @param entity The source cart entity.
     * @return A cart DTO.
     */
    @Mapping(target = "userId", expression =
            "java(entity.getUser() != null ? entity.getUser().getId() : null)")
    CartDto toDto(Cart entity);

    /**
     * Converts a {@link CartItem} to a {@link CartItemDto}.
     * @param cartItem The source cart item.
     * @return A cart item DTO.
     */
    CartItemDto toDto(CartItem cartItem);

    /**
     * Converts a {@link CartItem} to an {@link OrderItem}.
     * This is a critical step in the checkout process. The `id` and `order`
     * are ignored as they will be assigned later. The `price` is also ignored
     * here and will be set separately based on the book snapshot's price.
     * @param cartItem The source cart item.
     * @return A new order item entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "book", source = "cartItem.book")
    @Mapping(target = "price", ignore = true)
    OrderItem toOrderItem (CartItem cartItem);

    /**
     * An `@AfterMapping` method that links the `OrderItem` with the `OrderedBook`
     * to maintain the bidirectional relationship.
     * This method is called after the `toOrderItem` mapping completes.
     * @param orderItem The target order item.
     * @param cartItem The source cart item (used for context).
     */
    @AfterMapping
    default void linkOrderItemWithBook(@MappingTarget OrderItem orderItem, CartItem cartItem) {
        if (orderItem.getBook() != null) {
            orderItem.getBook().setOrderItem(orderItem);
        }
    }

    /**
     * Converts a {@link Book} entity into an {@link OrderedBook} snapshot.
     * This method copies the book's details, preserving the state at the time of the order.
     * The `orderItemId` and `orderItem` are ignored as they will be assigned by the
     * parent {@link OrderItem} entity.
     * @param book The source book entity.
     * @return A new ordered book snapshot.
     */
    @Mapping(target = "orderItemId", ignore = true)
    @Mapping(target = "orderItem", ignore = true)
    @Mapping(target = "originalBookId", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "publishedYear", source = "publishedYear")
    @Mapping(target = "priceAtOrder", source = "price")
    OrderedBook toOrderedBook(Book book);

    /**
     * Converts an {@link OrderedBook} snapshot back to a {@link BookDto}.
     * This method is used to display historical book data from an order.
     * Fields like `cover` and `isDeleted` are ignored since they are not present
     * in the snapshot.
     * @param orderedBook The source ordered book snapshot.
     * @return A detailed book DTO.
     */
    @Mapping(target = "id", source = "originalBookId")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "publishedYear", source = "publishedYear")
    @Mapping(target = "price", source = "priceAtOrder")
    @Mapping(target = "cover", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    BookDto toBookDto(OrderedBook orderedBook);

    /**
     * Converts a {@link Cart} entity into a new {@link Order} entity.
     * This method is a core part of the checkout process. It maps cart items
     * to order items and sets the initial status to `PENDING`.
     * @param cart The source cart entity.
     * @return A new order entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "user", source = "cart.user")
    @Mapping(target = "items", source = "cart.items")
    Order toOrder (Cart cart);

    /**
     * An `@AfterMapping` method that links all newly created {@link OrderItem}s
     * to their parent {@link Order} to maintain the bidirectional relationship.
     * This method is called after the `toOrder` mapping completes.
     * @param order The target order.
     * @param cart The source cart (used for context).
     */
    @AfterMapping
    default void linkOrderItems(@MappingTarget Order order, Cart cart) {
        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                item.setOrder(order);
            }
        }
    }

    /**
     * A utility method to map an {@link Order} entity to an {@link OrderDto}
     * and format its date for display.
     * @param entity The source order entity.
     * @return An order DTO with a formatted date.
     */
    default OrderDto mapOrderToDtoWithDate(Order entity) {
        OrderDto dto = toDto(entity);

        if (dto.getDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            dto.setFormattedDate(dto.getDate().format(formatter));
        }

        return dto;
    }

    /**
     * A utility method to map an {@link Order} entity to a shortened {@link OrderShowingDto}
     * and format its date for display.
     * @param entity The source order entity.
     * @return A shortened order DTO with a formatted date.
     */
    default OrderShowingDto mapOrderToShortedDtoWithDate(Order entity) {
        OrderShowingDto dto = toShortedDto(entity);

        if (dto.getDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            dto.setFormattedDate(dto.getDate().format(formatter));
        }
        return dto;
    }

}
