package ru.edu.homework.springcrud.repository;

import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(String partNumber);

    void delete(Product product) throws NotFoundException;

    void delete(String partNumber) throws NotFoundException;

    Product save(Product product);

    Iterable<Product> saveAll(Iterable<Product> products);

    boolean existsById(String partNumber);

    long count();

}
