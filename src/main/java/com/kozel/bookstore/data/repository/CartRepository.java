package com.kozel.bookstore.data.repository;

import com.kozel.bookstore.data.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A Spring Data JPA repository for the {@link Cart} entity.
 * This repository handles all data access operations related to shopping carts,
 * with a focus on efficient retrieval of cart data and its associated items.
 *
 * @see Cart
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Checks if a cart exists for a given user ID.
     *
     * @param userId The ID of the user.
     * @return {@code true} if a cart exists for the user, {@code false} otherwise.
     */
    boolean existsByUserId(Long userId);


    /**
     * Finds a cart by the user's ID, eagerly fetching its associated items,
     * the books within those items, and the user details.
     * This query is optimized to retrieve all related data in a single database call,
     * mitigating the N+1 select problem.
     *
     * @param userId The ID of the user who owns the cart.
     * @return An {@link Optional} containing the cart, or empty if not found.
     */
    @Query("SELECT c FROM Cart c " +
            "LEFT JOIN FETCH c.items ci " +
            "LEFT JOIN FETCH ci.book " +
            "LEFT JOIN FETCH c.user " +
            "WHERE c.user.id = :userId")
    Optional<Cart> findByUserId(Long userId);

    /**
     * Retrieves a paginated list of all carts, eagerly fetching their items
     * and the books within those items.
     *
     * @param pageable The pagination information.
     * @return A {@link Page} of carts with their items and books loaded.
     */
    @NonNull
    @Query("SELECT c FROM Cart c " +
            "LEFT JOIN FETCH c.items ci " +
            "LEFT JOIN FETCH ci.book")
    Page<Cart> findAllWithItems(@NonNull Pageable pageable);

    /**
     * Permanently deletes all cart items associated with a specific cart.
     * This operation is typically used to clear a cart's contents, for example,
     * after a checkout is completed.
     *
     * @param cartId The ID of the cart whose items should be deleted.
     */
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteItemsByCartId(Long cartId);
}
