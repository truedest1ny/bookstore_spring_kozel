package com.kozel.bookstore.data.repository;

import java.util.List;

public interface CrudRepository <V, T> {
    V save(T object);
    T findById(V id);
    List<T> findAll();
    T update(T object);
    void delete(T object);
}
