package com.dudev.jdbc.starter.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {

    List<E> findAll();

    Optional<E> findById(K id);

    void update(E entity);

    E save(E entity);
}
