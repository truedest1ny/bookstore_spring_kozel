package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.data.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    boolean existsByUserId(Long userId);

    @Query("SELECT c FROM Cart c " +
            "LEFT JOIN FETCH c.items ci " +
            "LEFT JOIN FETCH ci.book " +
            "LEFT JOIN FETCH c.user " +
            "WHERE c.user.id = :userId")
    Optional<Cart> findByUserId(Long userId);

    @NonNull
    @Query("SELECT c FROM Cart c " +
            "LEFT JOIN FETCH c.items ci " +
            "LEFT JOIN FETCH ci.book")
    List<Cart> findAllWithItems();

    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteItemsByCartId(Long cartId);
}
