package com.demoerp.erp.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    T save(T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    boolean existsById(ID id);
    void delete(T entity);
    List<T> saveAll(List<T> entities);
} 