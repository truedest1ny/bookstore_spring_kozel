package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.book.BookDto;
import com.kozel.bookstore.service.dto.cart.CartDto;

import java.util.List;

public interface CartService {
    List<CartDto> getAll();
    CartDto getById(Long id);
    CartDto findOrCreateByUserId(Long userId);
    CartDto findByUserId(Long userId);
    CartDto create(CartDto cartDto);
    CartDto update(CartDto cartDto);
    void delete(CartDto cartDto);
    void addItemToCart(CartDto cart, BookDto book, int quantity);
    CartDto addItemToUserCart(Long userId, Long bookId, int quantity);
    CartDto removeItemFromUserCart(Long userId, Long bookId);
    void updateCartTotalPrice(CartDto cart);
    CartDto mergeCartToUser(Long userId, CartDto cartDto);
    CartDto clearCartByUserId(Long userId);

}
