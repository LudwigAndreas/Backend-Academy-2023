package ru.bakcend.academy.repository;

import ru.bakcend.academy.exception.product.NotFoundException;
import ru.bakcend.academy.exception.product.ProductNotUniqueException;
import ru.bakcend.academy.model.Product;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(String id);

    void save(Product product) throws ProductNotUniqueException;

    void update(Product product) throws NotFoundException;

    void delete(String id) throws NotFoundException;
}
