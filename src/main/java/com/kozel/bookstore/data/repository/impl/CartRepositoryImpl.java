package com.kozel.bookstore.data.repository.impl;

import com.kozel.bookstore.data.entity.Cart;
import com.kozel.bookstore.data.repository.CartRepository;
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
public class CartRepositoryImpl implements CartRepository {

    private static final String GET_ALL =
            "SELECT c FROM Cart c" +
                    "LEFT JOIN FETCH c.items ci " +
                    "LEFT JOIN FETCH ci.book";
    private static final String GET_BY_USER_ID_JOIN_FETCH =
            "SELECT c FROM Cart c " +
                    "LEFT JOIN FETCH c.items ci " +
                    "LEFT JOIN FETCH ci.book " +
                    "LEFT JOIN FETCH c.user " +
                    "WHERE c.user.id = :id";
    private static final String COUNT_ALL =
            "SELECT COUNT (c) FROM Cart c";
    private static final String COUNT_BY_USER_ID =
            "SELECT COUNT(c) FROM Cart c WHERE c.user.id = :userId";
    private static final String DELETE_ITEMS_BY_CART_ID =
            "DELETE FROM CartItem ci WHERE ci.cart.id = :cartId";

    @PersistenceContext
    private EntityManager manager;

    @Override
    public boolean existsByUserId(Long userId) {
        Long result = manager.createQuery(COUNT_BY_USER_ID, Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
        return result > 0;
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        List<Cart> carts = manager.createQuery(GET_BY_USER_ID_JOIN_FETCH, Cart.class)
                .setParameter("id", userId)
                .getResultList();
        return carts.stream().findFirst();
    }

    @Override
    public long countAll() {
        return manager.createQuery(COUNT_ALL, Long.class).getSingleResult();
    }

    @Override
    public Cart save(Cart cart) {
        if (cart.getId() != null){
            manager.merge(cart);
        }
        else {
            manager.persist(cart);
        }
        return cart;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.ofNullable(manager.find(Cart.class, id));
    }

    @Override
    public List<Cart> findAll() {
            return manager.createQuery(GET_ALL, Cart.class).getResultList();
    }

    @Override
    public void delete(Cart cart) {
        if (manager.contains(cart)) {
            manager.remove(cart);
        } else {
            manager.remove(manager.merge(cart));
        }
    }

    @Override
    public void deleteItemsByCartId(Long cartId) {
        manager.createQuery(DELETE_ITEMS_BY_CART_ID)
                .setParameter("cartId", cartId)
                .executeUpdate();
    }
}
