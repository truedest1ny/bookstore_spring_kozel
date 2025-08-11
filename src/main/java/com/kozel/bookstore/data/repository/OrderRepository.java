package com.kozel.bookstore.data.repository;


import com.kozel.bookstore.data.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A Spring Data JPA repository for the {@link Order} entity.
 * This repository handles all data access operations related to orders,
 * supporting queries by user, status, and with optimized fetching of
 * related entities.
 *
 * @see Order
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Finds a paginated list of all orders placed by a specific user.
     * This query uses a {@code FETCH JOIN} to eagerly load the user details,
     * and a separate {@code countQuery} is provided for efficient pagination.
     *
     * @param pageable The pagination information.
     * @param userId The ID of the user.
     * @return A {@link Page} of orders for the specified user.
     */
    @Query(value = "SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.user ou " +
            "WHERE o.user.id = :userId",
            countQuery = "SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId")
    Page<Order> findByUserId(@NonNull Pageable pageable, Long userId);

    /**
     * Finds a paginated list of orders by their status.
     * This is useful for administrative tasks, such as retrieving all pending or
     * delivered orders. A separate {@code countQuery} is provided for efficient pagination.
     *
     * @param pageable The pagination information.
     * @param status The {@link Order.Status} to search for.
     * @return A {@link Page} of orders with the specified status.
     */
    @Query(value = "SELECT o FROM Order o " +
            "WHERE o.status = :status",
            countQuery = "SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Page<Order> findByStatus(@NonNull Pageable pageable, Order.Status status);

    /**
     * Finds a single order by its ID, eagerly fetching all its associated items,
     * the ordered books within those items, and the user details.
     * This query is optimized to retrieve all related data in a single database call,
     * mitigating the N+1 select problem.
     *
     * @param id The ID of the order to find.
     * @return An {@link Optional} containing the order with all its details, or empty if not found.
     */
    @NonNull
    @Query("SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.items oi " +
            "LEFT JOIN FETCH oi.book " +
            "LEFT JOIN FETCH o.user " +
            "WHERE o.id = :id")
    Optional<Order> findById(@NonNull Long id);

    /**
     * Retrieves a paginated list of all orders, eagerly fetching their associated
     * user details. A separate {@code countQuery} is provided for efficient pagination.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of all orders with their user details loaded.
     */
    @NonNull
    @Query(value = "SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.user",
            countQuery = "SELECT COUNT(o) FROM Order o")
    Page<Order> findAllWithDetails(@NonNull Pageable pageable);

    /**
     * Updates the status of a specific order.
     * This operation functions as a "soft-delete" or status change, as it modifies
     * the order's state without physically removing the record from the database.
     *
     * @param id The ID of the order to update.
     * @param status The new {@link Order.Status} to set.
     */
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :id")
    void softDelete(@NonNull Long id, Order.Status status);
}
