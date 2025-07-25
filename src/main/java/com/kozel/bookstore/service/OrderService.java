package com.kozel.bookstore.service;


import com.kozel.bookstore.service.dto.cart.CartDto;
import com.kozel.bookstore.service.dto.order.OrderDto;
import com.kozel.bookstore.service.dto.order.OrderShowingDto;
import com.kozel.bookstore.service.dto.user.UserSessionDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAll();
    List<OrderShowingDto> getOrdersDtoShort();
    OrderDto getById(Long id, UserSessionDto user);
    OrderDto create(CartDto cartDto);
    OrderDto update(OrderDto orderDto);
    void archive(Long orderId, UserSessionDto user);
    void approve(Long orderId, UserSessionDto user);
    void cancel(Long orderId, UserSessionDto user);
    List<OrderShowingDto> findByUserId(Long userId);
}
