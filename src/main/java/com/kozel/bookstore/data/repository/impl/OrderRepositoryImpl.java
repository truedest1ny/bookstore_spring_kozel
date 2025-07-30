package com.kozel.bookstore.data.repository.impl;


import com.kozel.bookstore.data.entity.Order;
import com.kozel.bookstore.data.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private static final String GET_BY_ID_JOIN_FETCH =
            "SELECT o FROM Order o " +
                    "LEFT JOIN FETCH o.items oi " +
                    "LEFT JOIN FETCH oi.book " +
                    "LEFT JOIN FETCH o.user " +
                    "WHERE o.id = :id";
    private static final String GET_ALL_JOIN_FETCH =
            "SELECT o FROM Order o " +
                    "LEFT JOIN FETCH o.items oi " +
                    "LEFT JOIN FETCH oi.book " +
                    "LEFT JOIN FETCH o.user";
    private static final String GET_BY_USER_ID_JOIN_FETCH =
            "SELECT o FROM Order o " +
                    "LEFT JOIN FETCH o.items oi " +
                    "LEFT JOIN FETCH o.user ou " +
                    "LEFT JOIN FETCH oi.book " +
                    "WHERE o.user.id = :id";
    private static final String COUNT_ALL =
            "SELECT COUNT (o) FROM Order o";
    private static final String GET_BY_STATUS_JOIN_FETCH =
            "SELECT o FROM Order o " +
                    "LEFT JOIN FETCH o.items oi " +
                    "LEFT JOIN FETCH oi.book " +
                    "WHERE o.status = :status";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Order> findByUserId(Long userId) {
        return manager.createQuery(GET_BY_USER_ID_JOIN_FETCH, Order.class)
                .setParameter("id", userId)
                .getResultList();
    }

    @Override
    public List<Order> findByStatus(Order.Status status) {
        return manager.createQuery(GET_BY_STATUS_JOIN_FETCH, Order.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public long countAll() {
        return manager.createQuery(COUNT_ALL, Long.class).getSingleResult();
    }

    @Override
    public Order save(Order order) {
        if (order.getId() != null){
            manager.merge(order);
        }
        else {
            manager.persist(order);
        }
        return order;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.of(manager.createQuery(GET_BY_ID_JOIN_FETCH, Order.class)
                .setParameter("id", id)
                .getSingleResult());
    }

    @Override
    public List<Order> findAll() {
       return manager.createQuery(GET_ALL_JOIN_FETCH, Order.class)
               .getResultList();
    }

    @Override
    public void delete(Order order) {
        order.setStatus(Order.Status.ARCHIVED);
        manager.merge(order);
    }
}
