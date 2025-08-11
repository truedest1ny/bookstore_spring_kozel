package com.kozel.bookstore.service.dto.cart;


import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A Data Transfer Object representing a user's shopping cart.
 * This DTO is used to transfer cart-related data to the client,
 * including a list of items, the associated user, and the total price.
 * It also includes utility methods for managing cart items.
 */
@Data
public class CartDto {

    /**
     * The unique identifier of the cart.
     */
    private Long id;

    /**
     * The unique identifier of the user to whom the cart belongs.
     */
    private Long userId;

    /**
     * A list of items currently in the cart.
     * The list is initialized to an empty ArrayList to prevent {@link NullPointerException}.
     */
    private List<CartItemDto> items = new ArrayList<>();

    /**
     * The total price of all items in the cart.
     * The value is initialized to zero.
     */
    private BigDecimal totalPrice = BigDecimal.ZERO;

    /**
     * Adds a new item to the cart.
     *
     * @param item The {@link CartItemDto} to add.
     */
    public void addItem(CartItemDto item) {
        items.add(item);
    }

    /**
     * Removes a specified item from the cart.
     *
     * @param item The {@link CartItemDto} to remove.
     */
    public void removeItem(CartItemDto item) {
        if (item == null) {
            return;
        }
        if (items != null) {
            items.remove(item);
        }
    }
}
