package ru.edu.homework.springcrud.repository;

import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.Product;

import java.util.List;
import java.util.Optional;

/*
 * I don't know if I can use jpa or other libraries that provide a standard repository interface.
 * Decided to write my own just in case.
 * */
public interface EducationalRepository<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    void delete(T entity) throws NotFoundException;

    void deleteById(ID id) throws NotFoundException;

    T save(T entity) throws NotFoundException, AlreadyExistsException;

    List<T> saveAll(List<T> products) throws NotFoundException, AlreadyExistsException;

    boolean existsById(ID id);

    long count();

}
