package com.kozel.bookstore.data.dao;

import java.util.List;

public interface CrudDao <V, T>{

    V save(T object);
    T findById(V id);
    List<T> findAll();
    T update(T object);
    boolean deleteById (V id);


}
