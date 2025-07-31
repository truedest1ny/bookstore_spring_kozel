package com.kozel.bookstore.service.dto.cart;


import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class CartDto {
    private Long id;
    private Long userId;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public void addItem(CartItemDto item) {
        items.add(item);
    }

    public void removeItem(CartItemDto item) {
        if (item == null) {
            return;
        }
        if (items != null) {
            items.remove(item);
        }
    }
}
