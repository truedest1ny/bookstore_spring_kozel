package com.kozel.bookstore.service;

import com.kozel.bookstore.service.dto.book.BookDto;
import com.kozel.bookstore.service.dto.cart.CartDto;
import com.kozel.bookstore.service.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * A service for managing shopping cart business logic.
 * This service provides methods for retrieving, manipulating, and
 * managing the lifecycle of user shopping carts.
 */
public interface CartService {

    /**
     * Retrieves all carts with their items in a paginated format.
     * This method is typically for administrative use.
     *
     * @param pageable The pagination information.
     * @return A paginated list of all carts.
     */
    Page<CartDto> getAll(Pageable pageable);

    /**
     * Retrieves a cart by its unique identifier.
     *
     * @param id The unique ID of the cart.
     * @return The {@link CartDto} for the specified ID.
     * @throws ResourceNotFoundException if no cart is found with the given ID.
     */
    CartDto getById(Long id);

    /**
     * Finds a cart by a user ID, or creates a new one if it doesn't exist.
     *
     * @param userId The unique ID of the user.
     * @return The existing or newly created {@link CartDto}.
     */
    CartDto findOrCreateByUserId(Long userId);

    /**
     * Finds a cart by a user ID.
     *
     * @param userId The unique ID of the user.
     * @return The {@link CartDto} for the specified user.
     * @throws ResourceNotFoundException if no cart is found for the given user ID.
     */
    CartDto findByUserId(Long userId);

    /**
     * Creates a new cart.
     *
     * @param cartDto The DTO containing cart data.
     * @return The newly created {@link CartDto}.
     * @throws RuntimeException if a cart with the given user ID already exists.
     * @throws ResourceNotFoundException if a book specified in the cart items is not found.
     */
    CartDto create(CartDto cartDto);

    /**
     * Updates an existing cart.
     *
     * @param cartDto The DTO containing updated cart data.
     * @return The updated {@link CartDto}.
     * @throws IllegalArgumentException if the DTO's ID is null.
     * @throws ResourceNotFoundException if the cart or a book within its items is not found.
     */
    CartDto update(CartDto cartDto);

    /**
     * Deletes a cart by its ID.
     *
     * @param id The unique ID of the cart to delete.
     * @throws IllegalArgumentException if the ID is null.
     * @throws ResourceNotFoundException if the cart is not found.
     */
    void delete(Long id);

    /**
     * Adds an item to an in-memory cart DTO.
     * This method updates the provided {@link CartDto} directly.
     *
     * @param cart The cart DTO to modify.
     * @param book The book DTO to add.
     * @param quantity The quantity of the book to add.
     * @throws IllegalArgumentException if the quantity is not positive.
     */
    void addItemToCart(CartDto cart, BookDto book, int quantity);

    /**
     * Adds an item to a user's cart.
     *
     * @param userId The ID of the user.
     * @param bookId The ID of the book to add.
     * @param quantity The quantity of the book.
     * @return The updated {@link CartDto}.
     * @throws ResourceNotFoundException if the book is not found.
     */
    CartDto addItemToUserCart(Long userId, Long bookId, int quantity);

    /**
     * Removes an item from a user's cart based on book ID.
     *
     * @param userId The ID of the user.
     * @param bookId The ID of the book to remove.
     * @return The updated {@link CartDto}.
     * @throws ResourceNotFoundException if the cart or the item is not found.
     */
    CartDto removeItemFromUserCart(Long userId, Long bookId);

    /**
     * Recalculates and updates the total price of all items in a cart DTO.
     * This method modifies the provided {@link CartDto} directly.
     *
     * @param cart The cart DTO to update.
     */
    void updateCartTotalPrice(CartDto cart);

    /**
     * Merges the contents of a temporary cart into a user's existing cart.
     *
     * @param userId The ID of the user.
     * @param cartDto The temporary cart to merge.
     * @return The merged {@link CartDto} of the user.
     * @throws ResourceNotFoundException if a book in the temporary cart is not found.
     */
    CartDto mergeCartToUser(Long userId, CartDto cartDto);

    /**
     * Clears all items from a user's cart.
     *
     * @param userId The ID of the user whose cart will be cleared.
     * @return The cleared {@link CartDto}.
     * @throws ResourceNotFoundException if the cart is not found.
     */
    CartDto clearCartByUserId(Long userId);

}
