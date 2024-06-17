package ru.bakcend.academy.service;

import ru.bakcend.academy.exception.product.NotFoundException;
import ru.bakcend.academy.exception.product.ProductNotUniqueException;
import ru.bakcend.academy.model.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(String partNumber, String name, Double cost, Integer number) throws ProductNotUniqueException;

    List<Product> readAllProducts();

    Product updateProduct(String partNumber, String name, Double cost, Integer number) throws  NotFoundException;

    void deleteProduct(String partNumber) throws NotFoundException;

}
