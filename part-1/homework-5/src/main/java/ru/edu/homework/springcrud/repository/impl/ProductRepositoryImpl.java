package ru.edu.homework.springcrud.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.edu.homework.springcrud.db.DataSource;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;
import ru.edu.homework.springcrud.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final DataSource dataSource;

    private final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    @Autowired
    public ProductRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> productList = List.copyOf(dataSource.getProducts().values());
        logger.debug("Read successfully, {} products found", productList.size());
        for (Product product : productList) {
            product.setProductCategory(dataSource.getProductCategories().get(product.getProductCategory().getId()));
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(String partNumber) {
        try {
            logger.debug("Searching for product with part number {}", partNumber);
            Product product = dataSource.getProducts().get(partNumber);
            product.setProductCategory(dataSource.getProductCategories().get(product.getProductCategory().getId()));
            return Optional.of(product);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Product product) throws NotFoundException {
        try {
            if (product.getPartNumber() == null || product.getPartNumber().isEmpty()) {
                throw new IllegalArgumentException("Product part number is null or empty");
            }
            Map<String, Product> products = dataSource.getProducts();
            if (!products.containsKey(product.getPartNumber())) {
                throw new NotFoundException("Product not found");
            }
            products.remove(product.getPartNumber());
            dataSource.saveProducts(products);
            logger.debug("Product with part number {} deleted", product.getPartNumber());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Product is null");
        }
    }

    @Override
    public void deleteById(String partNumber) throws NotFoundException {
        try {
            if (partNumber == null || partNumber.isEmpty()) {
                throw new IllegalArgumentException("Product part number is null or empty");
            }
            Map<String, Product> products = dataSource.getProducts();
            if (!products.containsKey(partNumber)) {
                throw new NotFoundException("Product not found");
            }
            products.remove(partNumber);
            dataSource.saveProducts(products);
            logger.debug("Product with part number {} deleted", partNumber);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Product is null");
        }
    }

    @Override
    public Product save(Product product) throws NotFoundException {
        saveProduct(product);
        logger.debug("Product with part number {} saved", product.getPartNumber());
        return product;
    }

    @Override
    public List<Product> saveAll(List<Product> products) throws NotFoundException {
        Map<String, Product> productsMap = dataSource.getProducts();
        Map<Long, ProductCategory> productCategoryMap = dataSource.getProductCategories();
        for (Product product : products) {
            if (product.getPartNumber() == null || product.getPartNumber().isEmpty()) {
                throw new IllegalArgumentException("Product part number is null or empty");
            }
            if (productsMap.containsKey(product.getPartNumber()) || products.stream().anyMatch(product1 -> product1.getPartNumber().equals(product.getPartNumber()) && !product1.equals(product))) {
                throw new NotFoundException("Product with part number " + product.getPartNumber() + " already exists");
            }
            if (productCategoryMap.containsKey(product.getProductCategory().getId())) {
                product.setProductCategory(productCategoryMap.get(product.getProductCategory().getId()));
            } else {
                throw new NotFoundException("Product category not found");
            }
        }
        for (Product product : products) {
            productsMap.put(product.getPartNumber(), product);
        }
        dataSource.saveProducts(productsMap);
        logger.debug("Product saved successfully, {} products saved", products.spliterator().getExactSizeIfKnown());
        return products;
    }

    private void saveProduct(Product product) throws NotFoundException {
        Map<String, Product> products = dataSource.getProducts();
        Map<Long, ProductCategory> productCategories = dataSource.getProductCategories();
        if (product.getPartNumber() == null || product.getPartNumber().isEmpty()) {
            throw new IllegalArgumentException("Product part number is null or empty");
        } else if (products.containsKey(product.getPartNumber())) {
            throw new IllegalArgumentException("Product with part number " + product.getPartNumber() + " already exists");
        }
        if (productCategories.containsKey(product.getProductCategory().getId())) {
            product.setProductCategory(productCategories.get(product.getProductCategory().getId()));
        } else {
            throw new NotFoundException("Product category not found");
        }
        products.put(product.getPartNumber(), product);
        dataSource.saveProducts(products);
    }

    @Override
    public boolean existsById(String partNumber) {
        logger.debug("Searching for product with part number {}", partNumber);
        return dataSource.getProducts().containsKey(partNumber);
    }

    @Override
    public long count() {
        return dataSource.getProducts().size();
    }

}
