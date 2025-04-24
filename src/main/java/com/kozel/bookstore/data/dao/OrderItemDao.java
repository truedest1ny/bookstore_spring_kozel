package com.kozel.bookstore.data.dao;


import com.kozel.bookstore.data.dto.OrderItemDto;

import java.util.List;

public interface OrderItemDao extends CrudDao<Long, OrderItemDto> {
    List<OrderItemDto> findByOrderId(Long orderId);
    long countAll();
}
