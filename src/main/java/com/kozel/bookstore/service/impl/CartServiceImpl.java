package com.kozel.bookstore.service.impl;

import com.kozel.bookstore.data.entity.Book;
import com.kozel.bookstore.data.entity.Cart;
import com.kozel.bookstore.data.entity.CartItem;
import com.kozel.bookstore.data.entity.User;
import com.kozel.bookstore.data.mapper.DataMapper;
import com.kozel.bookstore.data.repository.BookRepository;
import com.kozel.bookstore.data.repository.CartRepository;
import com.kozel.bookstore.data.repository.UserRepository;
import com.kozel.bookstore.service.CartService;
import com.kozel.bookstore.service.dto.book.BookDto;
import com.kozel.bookstore.service.dto.cart.CartDto;
import com.kozel.bookstore.service.dto.cart.CartItemDto;
import com.kozel.bookstore.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final DataMapper mapper;

    @Override
    public Page<CartDto> getAll(Pageable pageable) {
        return cartRepository.findAllWithItems(pageable)
                .map(mapper::toDto);
    }

    @Override
    public CartDto getById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Cannot find cart by id " + id)
        );
        updateCartTotalPrice(cart);
        return mapper.toDto(cart);
    }

    @Override
    public CartDto findOrCreateByUserId(Long userId) {
        Cart cart = getOrCreateCart(userId);
        updateCartTotalPrice(cart);
        return mapper.toDto(cart);
    }

    @Override
    public CartDto findByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Cart with user ID " + userId + " was not found!"));
        updateCartTotalPrice(cart);
        return mapper.toDto(cart);
    }

    @Override
    public CartDto create(CartDto cartDto) {
        if (cartRepository.existsByUserId(cartDto.getUserId())){
            throw new RuntimeException(
                    "Cart with user ID (" + cartDto.getUserId() + ") is already exists!");
        }

        Cart newCart = new Cart();
        User user = userRepository.getReferenceById(cartDto.getUserId());
        newCart.setUser(user);

        if (cartDto.getItems() != null && !cartDto.getItems().isEmpty()) {
            Map<Long, Book> bookMap = loadBooksForCartItems(cartDto.getItems());
            CartProcessingContext contextForCreate = new CartProcessingContext(
                    getBookIds(cartDto.getItems()),
                    bookMap,
                    Collections.emptyMap()
            );
            for (CartItemDto itemDto : cartDto.getItems()) {
                processItemAdding(itemDto, contextForCreate, newCart);
            }
        }
        updateCartTotalPrice(newCart);

        Cart savedCart = cartRepository.save(newCart);
        return mapper.toDto(savedCart);
    }

    @Override
    public CartDto update(CartDto cartDto) {
        if (cartDto.getId() == null) {
            throw new IllegalArgumentException(
                    "Cart ID must be provided for update operation.");
        }

        Cart existingCart = cartRepository.findById(cartDto.getId()).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Cart not found for update with ID: " + cartDto.getId()));

        CartProcessingContext cartProcessingContext =
                getContext(cartDto, existingCart);

        existingCart.getItems().removeIf(existingItem ->
                !cartProcessingContext.bookIds().contains(existingItem.getBook().getId()));

        for (CartItemDto itemDto : cartDto.getItems()) {
            processItemAdding(itemDto, cartProcessingContext, existingCart);
        }

        updateCartTotalPrice(existingCart);

        Cart savedCart = cartRepository.save(existingCart);
        return mapper.toDto(savedCart);

    }

    @Override
    public CartDto removeItemFromUserCart(Long userId, Long bookId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart for user ID " + userId + " not found."));

        CartItem itemToRemove = getExistingItem(bookId, cart);
        if (itemToRemove == null) {
            throw new ResourceNotFoundException(
                    "Item with book ID " + bookId + " was not found in cart with user ID " + userId);
        }
        cart.removeItem(itemToRemove);

        updateCartTotalPrice(cart);

        Cart savedCart = cartRepository.save(cart);
        return mapper.toDto(savedCart);
    }

    @Override
    public void delete(Long cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException(
                    "Cart ID must be provided for delete operation.");
        }

        if (!cartRepository.existsById(cartId)) {
            throw  new RuntimeException(
                    "Cart not found for delete with ID: " + cartId);
        }
        cartRepository.deleteById(cartId);
    }

    @Override
    public CartDto addItemToUserCart(Long userId, Long bookId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with ID: " + bookId));

        Cart cart = getOrCreateCart(userId);
        updateOrCollectCartItem(book, cart, quantity);
        updateCartTotalPrice(cart);

        Cart savedCart = cartRepository.save(cart);
        return mapper.toDto(savedCart);
    }



    @Override
    public void addItemToCart(CartDto cart, BookDto book, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        updateOrCollectCartItemDto(cart, book, quantity);
        updateCartTotalPrice(cart);
    }



    @Override
    public void updateCartTotalPrice(CartDto cart) {
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        for (CartItemDto item : cart.getItems()) {
            if (item.getBook() != null && item.getBook().getPrice() != null) {
                BigDecimal currentItemPrice = item.getBook().getPrice()
                                                .multiply(BigDecimal.valueOf(item.getQuantity()));
                item.setPrice(currentItemPrice);
            }
            cartTotalPrice = cartTotalPrice.add(item.getPrice());
        }
        cart.setTotalPrice(cartTotalPrice);
    }

    @Override
    public CartDto mergeCartToUser(Long userId, CartDto cartDto) {

        Cart userCartInDb = getOrCreateCart(userId);

        if (cartDto == null || cartDto.getItems() == null || cartDto.getItems().isEmpty()) {
            return mapper.toDto(userCartInDb);
        }

        CartProcessingContext cartProcessingContext = getContext(cartDto, userCartInDb);

        for (CartItemDto cartItemDto : cartDto.getItems()) {
            processItemAdding(cartItemDto, cartProcessingContext, userCartInDb);
        }

        updateCartTotalPrice(userCartInDb);

        cartRepository.save(userCartInDb);
        return mapper.toDto(userCartInDb);
    }

    @Override
    public CartDto clearCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .map(cart -> {
                    cartRepository.deleteItemsByCartId(cart.getId());
                    cart.setTotalPrice(BigDecimal.ZERO);
                    Cart savedCart = cartRepository.save(cart);
                    return mapper.toDto(savedCart);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart for user (" + userId + ") was not found for clearing"));
    }

    private CartItem getExistingItem(Long bookId, Cart cart) {
        return cart.getItems().stream()
                .filter(cartItem -> cartItem.getBook().getId().equals(bookId))
                .findFirst()
                .orElse(null);
    }

    private void updateOrCollectCartItem(Book book, Cart cart, int quantity) {
        CartItem existingItem = getExistingItem(book.getId(), cart);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setPrice(book.getPrice().multiply(BigDecimal.valueOf(existingItem.getQuantity())));
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setBook(book);
            newCartItem.setQuantity(quantity);
            newCartItem.setPrice(book.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cart.addItem(newCartItem);
        }
    }

    private void processItemAdding(CartItemDto cartItemDto,
                                   CartProcessingContext cartProcessingContext,
                                   Cart targetCart) {
        Long bookId = cartItemDto.getBook().getId();
        Book book = cartProcessingContext.loadedBooksMap().get(bookId);
        if (book == null) {
            throw new ResourceNotFoundException(
                    "Book with ID " + bookId + " was not found during cart process.");
        }
        updateOrCollectCartItem(book, targetCart, cartItemDto.getQuantity());
    }

    private void updateOrCollectCartItemDto(CartDto cart, BookDto book, int quantity) {
        boolean isFoundItem = false;
        for (CartItemDto item : cart.getItems()) {
            if (item.getBook().getId().equals(book.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setPrice(book.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                isFoundItem = true;
                break;
            }
        }
        if (!isFoundItem) {
            CartItemDto newItem = new CartItemDto();
            newItem.setBook(book);
            newItem.setQuantity(quantity);
            newItem.setPrice(book.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cart.getItems().add(newItem);
        }
    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userRepository.getReferenceById(userId));
                    return cartRepository.save(newCart);
                });
    }

    private Set<Long> getBookIds(List<CartItemDto> cartDto) {
        return cartDto.stream()
                .map(itemDto -> itemDto.getBook().getId())
                .collect(Collectors.toSet());
    }

    private Map<Long, Book> loadBooksForCartItems(List<CartItemDto> itemsDtos) {
        if (itemsDtos == null || itemsDtos.isEmpty()) {
            return Collections.emptyMap();
        }
        Set<Long> bookIds = getBookIds(itemsDtos);
        List<Book> books = bookRepository.findAllByIds(bookIds);
        return books.stream()
                .collect(Collectors.toMap(Book::getId, Function.identity()));

    }

    private Map<Long, CartItem> mapExistingCartItemsByBookId(Collection<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return Collections.emptyMap();
        }
        return cartItems.stream()
                .collect(Collectors.toMap(item -> item.getBook().getId(), Function.identity()));
    }

    private CartProcessingContext getContext(CartDto cartDto, Cart existingCart) {
        Set<Long> bookIds = getBookIds(cartDto.getItems());
        Map<Long, Book> loadedBooksMap = loadBooksForCartItems(cartDto.getItems());
        Map<Long, CartItem> existingItemsMap = mapExistingCartItemsByBookId(existingCart.getItems());
        return new CartProcessingContext(bookIds, loadedBooksMap, existingItemsMap);
    }

    private record CartProcessingContext(
            Set<Long> bookIds,
            Map<Long, Book> loadedBooksMap,
            Map<Long, CartItem> existingItemsMap) {
    }


    private void updateCartTotalPrice(Cart existingCart) {
        BigDecimal newTotalPrice = BigDecimal.ZERO;
        for (CartItem item : existingCart.getItems()) {
            if (item.getBook() != null && item.getBook().getPrice() != null) {
                BigDecimal currentItemPrice = item.getBook().getPrice()
                                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                item.setPrice(currentItemPrice);
            }
            newTotalPrice = newTotalPrice.add(item.getPrice());
        }
        existingCart.setTotalPrice(newTotalPrice);
    }
}
