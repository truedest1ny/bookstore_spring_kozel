package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.data.entity.Cart;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Long, Cart>{
    boolean existsByUserId(Long userId);
    Optional<Cart> findByUserId(Long userId);
    long countAll();
    void deleteItemsByCartId(Long cartId);
}
