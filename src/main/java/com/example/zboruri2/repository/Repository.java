package com.example.zboruri2.repository;

import com.example.zboruri2.model.Entity;

import java.util.List;
import java.util.Optional;

public interface Repository <ID extends Comparable<ID>, E extends Entity<ID>> {
    ID getLastId();

    Optional<E> findOne(ID id);
    List<E> findAll();
    Optional<E> save(E entity);
}
