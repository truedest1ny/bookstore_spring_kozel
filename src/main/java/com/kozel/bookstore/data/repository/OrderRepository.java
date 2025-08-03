package com.kozel.bookstore.data.repository;


import com.kozel.bookstore.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH o.user ou " +
            "LEFT JOIN FETCH oi.book " +
            "WHERE o.user.id = :userId")
    List<Order> findByUserId(Long userId);

    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH oi.book " +
            "WHERE o.status = :status")
    List<Order> findByStatus(Order.Status status);

    @NonNull
    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH oi.book " +
            "LEFT JOIN FETCH o.user " +
            "WHERE o.id = :id")
    Optional<Order> findById(@NonNull Long id);

    @NonNull
    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH oi.book " +
            "LEFT JOIN FETCH o.user")
    List<Order> findAllWithDetails();

    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    void softDelete(@NonNull Long id, Order.Status status);
}
