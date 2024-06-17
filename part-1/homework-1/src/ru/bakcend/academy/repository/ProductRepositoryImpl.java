package ru.bakcend.academy.repository;

import ru.bakcend.academy.exception.product.NotFoundException;
import ru.bakcend.academy.exception.product.ProductNotUniqueException;
import ru.bakcend.academy.model.Product;

import java.util.*;

public class ProductRepositoryImpl implements ProductRepository {
    Map<String, Product> repo = new HashMap<>();
    public List<Product> findAll() {
        return new ArrayList<>(repo.values());
    }

    @Override
    public Optional<Product> findById(String id) {
        try {
            return Optional.of(repo.get(id));
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Product product) throws ProductNotUniqueException {
        if (repo.containsKey(product.getPartNumber())) {
            throw new ProductNotUniqueException("Product with part number " + product.getPartNumber() + " already exists");
        }
        repo.put(product.getPartNumber(), product);
    }

    @Override
    public void update(Product product) throws NotFoundException {
        if (!repo.containsKey(product.getPartNumber())) {
            throw new NotFoundException("Product with part number " + product.getPartNumber() + " not found");
        }
        repo.replace(product.getPartNumber(), product);
    }

    @Override
    public void delete(String id) throws NotFoundException {
        try {
            if (id == null || id.isEmpty()) {
                throw new IllegalArgumentException("Part number is empty");
            } else if (!repo.containsKey(id)) {
                throw new NotFoundException("Product with part number " + id + " not found");
            }
            repo.remove(id);
        } catch (NullPointerException e) {
            throw new NotFoundException("Product with part number " + id + " not found");
        }
    }
}
