package com.kozel.bookstore.data.repository;

import org.hibernate.Filter;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public interface CrudRepository <V, T>  {
    T save(T object);
    Optional<T> findById(V id);
    List<T> findAll();
    void delete(T object);

    default void disableDeletedFilter(Session session) {
        session.disableFilter("isDeletedFilter");
    }

    default void activateDeletedFilter(Session session, boolean flag) {
        Filter filter = session.enableFilter("isDeletedFilter");
        filter.setParameter("isDeleted", flag);
    }
}
