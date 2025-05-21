package com.kozel.bookstore.service;


import com.kozel.bookstore.service.dto.OrderDto;
import com.kozel.bookstore.service.dto.OrderShowingDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAll();
    List<OrderShowingDto> getOrdersDtoShort();
    OrderDto getById(Long id);
    Long create(OrderDto orderDto);
    OrderDto update(OrderDto orderDto);
    void disable(Long id);
    List<OrderShowingDto> findByUserId(Long userId);
}
