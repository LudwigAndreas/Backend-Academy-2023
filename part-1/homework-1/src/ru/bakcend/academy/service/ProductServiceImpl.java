package ru.bakcend.academy.service;

import ru.bakcend.academy.exception.product.NotFoundException;
import ru.bakcend.academy.exception.product.ProductNotUniqueException;
import ru.bakcend.academy.model.Product;
import ru.bakcend.academy.repository.ProductRepository;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Product saveProduct(String partNumber, String name, Double cost, Integer number) throws ProductNotUniqueException {
        if (productRepository.findById(partNumber).isPresent()) {
            throw new ProductNotUniqueException("Product with part number " + partNumber + " already exists");
        }
        Product product = new Product(partNumber, name, cost, number);
        productRepository.save(product);
        return product;
    }

    @Override
    public List<Product> readAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(String partNumber, String name, Double cost, Integer number) throws NotFoundException {
        if (!productRepository.findById(partNumber).isPresent()) {
            throw new NotFoundException("Product with part number " + partNumber + " not found");
        }
        Product product = new Product(partNumber, name, cost, number);
        productRepository.update(product);
        return product;
    }

    @Override
    public void deleteProduct(String partNumber) throws NotFoundException {
        productRepository.delete(partNumber);
    }
}
