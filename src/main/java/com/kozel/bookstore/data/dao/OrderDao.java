package com.kozel.bookstore.data.dao;

import com.kozel.bookstore.data.dto.OrderDto;

import java.util.List;

public interface OrderDao extends CrudDao<Long, OrderDto>{
    List<OrderDto> findByUserId(Long userId);
    List<OrderDto> findByStatus(OrderDto.Status status);
    long countAll();
}
