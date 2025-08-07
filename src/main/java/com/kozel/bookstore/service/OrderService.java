package com.kozel.bookstore.service;


import com.kozel.bookstore.service.dto.order.OrderDto;
import com.kozel.bookstore.service.dto.order.OrderShowingDto;
import com.kozel.bookstore.service.dto.user.UserSessionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderDto> getAll(Pageable pageable);
    Page<OrderShowingDto> getOrdersDtoShort(Pageable pageable);
    OrderDto getById(Long id, UserSessionDto user);
    OrderDto create(Long userId);
    OrderDto update(OrderDto orderDto);
    void archive(Long orderId, UserSessionDto user);
    void approve(Long orderId, UserSessionDto user);
    void cancel(Long orderId, UserSessionDto user);
    Page<OrderShowingDto> findByUserId(Pageable pageable, Long userId);
}
