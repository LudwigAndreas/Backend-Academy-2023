package ru.edu.homework.springcrud.service;

import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(String partNumber) throws NotFoundException;

    Product createProduct(Product productDto) throws AlreadyExistsException, NotFoundException;

    Product updateProduct(String partNumber, Product productDto) throws NotFoundException, AlreadyExistsException;

    void deleteProduct(String partNumber) throws NotFoundException;
}
