package ru.seminar.homework.hw5.repository;

import ru.seminar.homework.hw5.exception.AlreadyExistsException;
import ru.seminar.homework.hw5.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

public interface EducationalRepository<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    void delete(T entity) throws NotFoundException;

    void deleteById(ID id) throws NotFoundException;

    T save(T entity) throws NotFoundException, AlreadyExistsException;

    List<T> saveAll(List<T> objects) throws NotFoundException, AlreadyExistsException;

    boolean existsById(ID id);

    long count();

}