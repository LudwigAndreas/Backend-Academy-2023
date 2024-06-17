package ru.edu.homework.springcrud.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RamProductRepositoryImpl implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(RamProductRepositoryImpl.class);

    @Override
    public List<Product> findAll() {
        List<Product> productList = List.copyOf(products.values());
        logger.debug("Ram read successfully, {} products found", productList.size());
        return productList;
    }

    @Override
    public Optional<Product> findById(String partNumber) {
        try {
            logger.debug("Searching for product with part number {}", partNumber);
            return Optional.of(products.get(partNumber));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Product product) throws NotFoundException {
        try {
            if (product.getPartNumber() == null || product.getPartNumber().isEmpty()) {
                throw new IllegalArgumentException("Product part number is null or empty");
            } else if (!products.containsKey(product.getPartNumber())) {
                throw new NotFoundException("Product not found");
            }
            products.remove(product.getPartNumber());
            logger.debug("Product with part number {} deleted", product.getPartNumber());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Product is null");
        }
    }

    @Override
    public void delete(String partNumber) throws NotFoundException {
        try {
            if (partNumber == null || partNumber.isEmpty()) {
                throw new IllegalArgumentException("Product part number is null or empty");
            } else if (!products.containsKey(partNumber)) {
                throw new NotFoundException("Product not found");
            }
            products.remove(partNumber);
            logger.debug("Product with part number {} deleted", partNumber);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Product is null");
        }
    }

    @Override
    public Product save(Product product) {
        products.put(product.getPartNumber(), product);
        logger.debug("Product with part number {} saved", product.getPartNumber());
        return product;
    }

    @Override
    public Iterable<Product> saveAll(Iterable<Product> products) {
        products.forEach(product -> this.products.put(product.getPartNumber(), product));
        logger.debug("Product saved successfully, {} products saved", products.spliterator().getExactSizeIfKnown());
        return products;
    }

    @Override
    public boolean existsById(String partNumber) {
        logger.debug("Searching for product with part number {}", partNumber);
        return products.containsKey(partNumber);
    }

    @Override
    public long count() {
        return products.size();
    }
}
