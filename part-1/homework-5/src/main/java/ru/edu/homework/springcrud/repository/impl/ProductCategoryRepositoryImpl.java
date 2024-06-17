package ru.edu.homework.springcrud.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.edu.homework.springcrud.db.DataSource;
import ru.edu.homework.springcrud.db.ProductCategoryIdGenerator;
import ru.edu.homework.springcrud.db.annotation.AnnotationProcessor;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;
import ru.edu.homework.springcrud.repository.ProductCategoryRepository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProductCategoryRepositoryImpl implements ProductCategoryRepository {

    private final DataSource dataSource;

    private final Logger logger = LoggerFactory.getLogger(ProductCategoryRepositoryImpl.class);

    @Autowired
    public ProductCategoryRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        if (dataSource.getProductCategories().isEmpty()) {
            Map<Long, ProductCategory> productCategories = dataSource.getProductCategories();
            productCategories.put(0L, new ProductCategory(0L, "Default", "default", new HashSet<>()));
            dataSource.saveProductCategories(productCategories);
        }
    }

    @Override
    public List<ProductCategory> findAll() {
        List<ProductCategory> productCategoryList = List.copyOf(dataSource.getProductCategories().values());
        List<Product> productList = List.copyOf(dataSource.getProducts().values());
        for (ProductCategory productCategory : productCategoryList) {
            productCategory.setProducts(productList.stream().filter(product -> product.getProductCategory().getId().equals(productCategory.getId())).collect(Collectors.toSet()));
        }
        logger.debug("Ram read successfully, {} product categories found", productCategoryList.size());
        return productCategoryList;
    }

    @Override
    public Optional<ProductCategory> findById(Long id) {
        try {
            logger.debug("Searching for product category with id {}", id);
            ProductCategory productCategory = dataSource.getProductCategories().get(id);
            productCategory.setProducts(dataSource.getProducts().values().stream().filter(product -> product.getProductCategory().getId().equals(productCategory.getId())).collect(Collectors.toSet()));
            return Optional.of(productCategory);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(ProductCategory entity) throws NotFoundException {
        try {
            if (entity.getId() == null) {
                throw new IllegalArgumentException("Product category id is null");
            }
            Map<Long, ProductCategory> productCategories = dataSource.getProductCategories();
            if (!productCategories.containsKey(entity.getId())) {
                throw new NotFoundException("Product category not found");
            }
            Map<String, Product> products = dataSource.getProducts();
            for (Product product : products.values()) {
                if (product.getProductCategory().getId().equals(entity.getId())) {
                    product.setProductCategory(productCategories.get(0L));
                }
            }
            productCategories.remove(entity.getId());
            dataSource.saveProductCategories(productCategories);
            logger.debug("Product category with id {} deleted", entity.getId());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Product category is null");
        }
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        try {
            if (id == null) {
                throw new IllegalArgumentException("Product category id is null");
            }
            Map<Long, ProductCategory> productCategories = dataSource.getProductCategories();
            if (!productCategories.containsKey(id)) {
                throw new NotFoundException("Product category not found");
            }
            Map<String, Product> products = dataSource.getProducts();
            for (Product product : products.values()) {
                if (product.getProductCategory().getId().equals(id)) {
                    product.setProductCategory(productCategories.get(0L));
                }
            }
            productCategories.remove(id);
            dataSource.saveProductCategories(productCategories);
            logger.debug("Product category with id {} deleted", id);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Product category id is null");
        }
    }

    @Override
    public ProductCategory save(ProductCategory entity) throws AlreadyExistsException {
        try {
            Map<Long, ProductCategory> productCategories = dataSource.getProductCategories();
            if (entity.getId() == null) {
                while (true) {
                    if (!productCategories.containsKey(ProductCategoryIdGenerator.getInstance().generateId())) {
                        break;
                    }
                }
                entity.setId(ProductCategoryIdGenerator.getInstance().generateId());
            }
            if (!AnnotationProcessor.isUnique(entity, dataSource.getProductCategories())) {
                throw new AlreadyExistsException("Product category already exists");
            }
            productCategories.put(entity.getId(), entity);
            dataSource.saveProductCategories(productCategories);
            logger.debug("Product category with id {} saved", entity.getId());
            return entity;
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Product category is null");
        }
    }

    @Override
    public List<ProductCategory> saveAll(List<ProductCategory> productsCategories) throws AlreadyExistsException {
        Map<Long, ProductCategory> productCategoriesDb = dataSource.getProductCategories();
        for (ProductCategory productCategory : productsCategories) {
            if (!AnnotationProcessor.isUnique(productCategory, dataSource.getProductCategories())) {
                throw new AlreadyExistsException("Product category already exists");
            }
        }
        try {
            productsCategories.forEach(product -> {
                if (product.getId() == null) {
                    while (true) {
                        if (!productCategoriesDb.containsKey(ProductCategoryIdGenerator.getInstance().generateId())) {
                            break;
                        }
                    }
                    product.setId(ProductCategoryIdGenerator.getInstance().generateId());
                }
                if (AnnotationProcessor.isUnique(product, productCategoriesDb))
                    productCategoriesDb.put(product.getId(), product);
                else {
                    throw new IllegalArgumentException("Product category already exists");
                }
            });
            dataSource.saveProductCategories(productCategoriesDb);
        } catch (IllegalArgumentException e) {
            throw new AlreadyExistsException("Product category is null");
        }
        logger.debug("Product categories saved successfully, {} product categories saved", productsCategories.spliterator().getExactSizeIfKnown());
        return productsCategories;
    }

    @Override
    public boolean existsById(Long id) {
        logger.debug("Searching for product category with id {}", id);
        return dataSource.getProductCategories().containsKey(id);
    }

    @Override
    public long count() {
        return dataSource.getProductCategories().size();
    }

    @Override
    public Optional<ProductCategory> findByUrl(String url) {
        try {
            logger.debug("Searching for product category with url {}", url);
            return dataSource.getProductCategories().values().stream().filter(productCategory -> productCategory.getPath().equals(url)).findFirst();
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }

}
