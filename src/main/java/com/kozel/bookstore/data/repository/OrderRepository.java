package com.kozel.bookstore.data.repository;


import com.kozel.bookstore.data.entity.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Long, Order>{
    List<Order> findByUserId(Long userId);
    List<Order> findByStatus(Order.Status status);
    long countAll();
}
