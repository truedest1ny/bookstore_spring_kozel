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

    private static final String GET_ALL =
            "SELECT o FROM Order o";
    private static final String GET_BY_USER_ID =
            "SELECT o FROM Order o WHERE o.user.id = :id";
    private static final String COUNT_ALL =
            "SELECT COUNT (o) FROM Order o";
    private static final String GET_BY_STATUS =
            "SELECT o FROM Order o Where o.status = :status";

    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<Order> findByUserId(Long userId) {
        return manager.createQuery(GET_BY_USER_ID, Order.class)
                .setParameter("id", userId)
                .getResultList();
    }

    @Override
    public List<Order> findByStatus(Order.Status status) {
        return manager.createQuery(GET_BY_STATUS, Order.class)
                .setParameter("status", status)
                .getResultList();
    }

    @Override
    public long countAll() {
        return manager.createQuery(COUNT_ALL, Long.class).getSingleResult();
    }

    @Override
    public Order save(Order object) {
        //TODO Order Save
        return object;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(manager.find(Order.class, id));
    }

    @Override
    public List<Order> findAll() {
       return manager.createQuery(GET_ALL, Order.class).getResultList();
    }


    @Override
    public void delete(Order object) {
        //TODO Order Delete
    }

}
