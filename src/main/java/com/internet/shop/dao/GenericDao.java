package com.internet.shop.dao;

import com.internet.shop.model.User;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, K> {
    T create(T item);

    Optional<T> getById(K id);

    List<T> getAll();

    T update(T item);

    boolean delete(K id);
}
