package com.kozel.bookstore.data.repository;


import com.kozel.bookstore.data.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.user ou " +
            "WHERE o.user.id = :userId",
            countQuery = "SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId")
    Page<Order> findByUserId(@NonNull Pageable pageable, Long userId);

    @Query(value = "SELECT o FROM Order o " +
            "WHERE o.status = :status",
            countQuery = "SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Page<Order> findByStatus(@NonNull Pageable pageable, Order.Status status);

    @NonNull
    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH oi.book " +
            "LEFT JOIN FETCH o.user " +
            "WHERE o.id = :id")
    Optional<Order> findById(@NonNull Long id);

    @NonNull
    @Query(value = "SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.user",
            countQuery = "SELECT COUNT(o) FROM Order o")
    Page<Order> findAllWithDetails(@NonNull Pageable pageable);

    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    void softDelete(@NonNull Long id, Order.Status status);
}
