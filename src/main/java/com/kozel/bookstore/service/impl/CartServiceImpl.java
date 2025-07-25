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
import com.kozel.bookstore.service.dto.BookDto;
import com.kozel.bookstore.service.dto.CartDto;
import com.kozel.bookstore.service.dto.CartItemDto;
import com.kozel.bookstore.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final DataMapper mapper;

    @Override
    public List<CartDto> getAll() {
        return cartRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CartDto getById(Long id) {
        log.debug("Called getById() method");
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Cannot find cart by id " + id)
        );
        return mapper.toDto(cart);
    }

    @Override
    public CartDto findOrCreateByUserId(Long userId) {
        log.debug("Called findOrCreateByUserId() method");
        Cart cart = getOrCreateCart(userId);
        return mapper.toDto(cart);
    }

    @Override
    public CartDto findByUserId(Long userId) {
        log.debug("Called findByUserId() method");
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Cart with user ID " + userId + " was not found!"));
        return mapper.toDto(cart);
    }

    @Override
    public CartDto create(CartDto cartDto) {
        log.debug("Called create() method");
        if (cartRepository.existsByUserId(cartDto.getUserId())){
            throw new RuntimeException(
                    "Cart with user ID (" + cartDto.getUserId() + ") is already exists!");
        }

        Cart newCart = new Cart();
        User user = userRepository.getReferenceById(cartDto.getUserId());
        newCart.setUser(user);

        initializeCartItems(cartDto, newCart);
        updateCartTotalPrice(newCart);

        Cart savedCart = cartRepository.save(newCart);
        return mapper.toDto(savedCart, cartDto.getUserId());
    }

    @Override
    public CartDto update(CartDto cartDto) {
        log.debug("Called update() method");
        if (cartDto.getId() == null) {
            throw new IllegalArgumentException(
                    "Cart ID must be provided for update operation.");
        }

        Cart existingCart = cartRepository.findById(cartDto.getId()).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Cart not found for update with ID: " + cartDto.getId()));

        CartProcessingContext cartProcessingContext =
                getContext(cartDto, existingCart);

        List<CartItem> itemsToRemove = existingCart.getItems().stream()
                .filter(existingItem ->
                        !cartProcessingContext.bookIds().contains(existingItem.getBook().getId()))
                .toList();

        itemsToRemove.forEach(existingCart::removeItem);

        for (CartItemDto itemDto : cartDto.getItems()) {

            Long bookId = itemDto.getBook().getId();
            Book book = cartProcessingContext.loadedBooksMap().get(bookId);

            if (book == null) {
                throw new ResourceNotFoundException(
                        "Book with ID " + bookId + " not found for cart item update.");
            }

            CartItem existingItem = cartProcessingContext.existingItemsMap().get(bookId);

            if (existingItem != null) {
                existingItem.setQuantity(itemDto.getQuantity());
                existingItem.setPrice(
                        book.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
            } else {
                collectCartItem(itemDto, book, existingCart);
            }
        }

        updateCartTotalPrice(existingCart);

        Cart savedCart = cartRepository.save(existingCart);
        return mapper.toDto(savedCart, savedCart.getUser().getId());

    }

    @Override
    public CartDto removeItemFromUserCart(Long userId, Long bookId) {
        log.debug("Called removeItemFromUserCart() method");
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart for user ID " + userId + " not found."));

        CartItem itemToRemove = getExistingItem(bookId, cart);
        cart.removeItem(itemToRemove);

        updateCartTotalPrice(cart);

        Cart savedCart = cartRepository.save(cart);
        return mapper.toDto(savedCart, userId);
    }

    @Override
    public void delete(CartDto cartDto) {
        log.debug("Called delete() method");
        if (cartDto.getId() == null) {
            throw new IllegalArgumentException(
                    "Cart ID must be provided for delete operation.");
        }
        Cart cartToDelete = cartRepository.findById(cartDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart not found for delete with ID: " + cartDto.getId()));
        cartRepository.delete(cartToDelete);
    }

    @Override
    public CartDto addItemToUserCart(Long userId, Long bookId, int quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with ID: " + bookId));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userRepository.getReferenceById(userId));
                    return cartRepository.save(newCart);
                });

        CartItem existingItem = getExistingItem(bookId, cart);

        addOrUpdateCartItem(quantity, existingItem, book, cart);
        updateCartTotalPrice(cart);

        Cart savedCart = cartRepository.save(cart);
        return mapper.toDto(savedCart, userId);
    }



    @Override
    public void addItemToCart(CartDto cart, BookDto book, int quantity) {
        log.debug("Called addItemToCart() method");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        updateOrCollectCartItemDto(cart, book, quantity);
        updateCartTotalPrice(cart);
    }



    @Override
    public void updateCartTotalPrice(CartDto cart) {
        log.debug("Called updateCartTotalPrice() method");
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        for (CartItemDto item : cart.getItems()) {
            cartTotalPrice = cartTotalPrice.add(item.getPrice());
        }
        cart.setTotalPrice(cartTotalPrice);
    }

    @Override
    public CartDto mergeCartToUser(Long userId, CartDto cartDto) {
        log.debug("Called mergeCartToUser() method");

        Cart userCartInDb = getOrCreateCart(userId);

        if (cartDto == null || cartDto.getItems() == null || cartDto.getItems().isEmpty()) {
            return mapper.toDto(userCartInDb);
        }

        CartProcessingContext cartProcessingContext = getContext(cartDto, userCartInDb);

        for (CartItemDto cartItemDto : cartDto.getItems()) {
            Long bookId = cartItemDto.getBook().getId();

            Book book = cartProcessingContext.loadedBooksMap().get(bookId);
            if (book == null) {
                throw new ResourceNotFoundException(
                        "Book with ID " + bookId + " was not found during cart merging.");
            }

            CartItem userCartItem = cartProcessingContext.existingItemsMap().get(bookId);

            if (userCartItem != null) {
                userCartItem.setQuantity(userCartItem.getQuantity() + cartItemDto.getQuantity());
                userCartItem.setPrice(book.getPrice().multiply(BigDecimal.valueOf(userCartItem.getQuantity())));

            } else {
                collectCartItem(cartItemDto, book, userCartInDb);
            }
        }

        updateCartTotalPrice(userCartInDb);

        cartRepository.save(userCartInDb);
        return mapper.toDto(userCartInDb, userId);
    }

    @Override
    public CartDto clearCartByUserId(Long userId) {
        log.debug("Called clearCartByUserId() method");
        return cartRepository.findByUserId(userId)
                .map(cart -> {
                    cartRepository.deleteItemsByCartId(cart.getId());
                    cart.setTotalPrice(BigDecimal.ZERO);
                    Cart savedCart = cartRepository.save(cart);
                    return mapper.toDto(savedCart, userId);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cart for user (" + userId + ")not found for clearing"));
    }

    private CartItem getExistingItem(Long bookId, Cart cart) {
        CartItem item = null;
        for (CartItem cartItem : cart.getItems()) {
            if (cartItem.getBook().getId().equals(bookId)) {
                item = cartItem;
                break;
            }
        }
        return item;
    }

    private void addOrUpdateCartItem(int quantity, CartItem existingItem, Book book, Cart cart) {
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setPrice(
                    book.getPrice().multiply(BigDecimal.valueOf(existingItem.getQuantity())));
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setBook(book);
            newCartItem.setQuantity(quantity);
            newCartItem.setPrice(book.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cart.addItem(newCartItem);
        }
    }

    private void initializeCartItems(CartDto cartDto, Cart newCart) {
        if (cartDto.getItems() == null || cartDto.getItems().isEmpty()) {
            return;
        }

        Map<Long, Book> bookMap = loadBooksForCartItems(cartDto.getItems());

        for (CartItemDto itemDto : cartDto.getItems()) {
            Book book = bookMap.get(itemDto.getBook().getId());
            if (book == null) {
                throw new ResourceNotFoundException(
                        "Book with ID " + itemDto.getBook().getId() + " not found for new cart item.");
            }
            collectCartItem(itemDto, book, newCart);
        }

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
            collectCartItemDto(cart, book, quantity);
        }
    }

    private void collectCartItemDto(CartDto cart, BookDto book, int quantity) {
        CartItemDto newItem = new CartItemDto();
        newItem.setBook(book);
        newItem.setQuantity(quantity);
        newItem.setPrice(book.getPrice().multiply(BigDecimal.valueOf(quantity)));
        cart.getItems().add(newItem);
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
        BigDecimal newTotalPrice = existingCart.getItems().stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        existingCart.setTotalPrice(newTotalPrice);
    }

    private void collectCartItem(CartItemDto cartItemDto, Book book, Cart userCartInDb) {
        CartItem newCartItem = new CartItem();
        newCartItem.setBook(book);
        newCartItem.setQuantity(cartItemDto.getQuantity());
        newCartItem.setPrice(book.getPrice().multiply(BigDecimal.valueOf(cartItemDto.getQuantity())));
        userCartInDb.addItem(newCartItem);
    }
}
