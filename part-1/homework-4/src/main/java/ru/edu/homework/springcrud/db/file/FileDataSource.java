package ru.edu.homework.springcrud.db.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.edu.homework.springcrud.db.DataSource;
import ru.edu.homework.springcrud.db.entity.ProductCategoryEntity;
import ru.edu.homework.springcrud.db.entity.ProductEntity;
import ru.edu.homework.springcrud.exception.db.DataSourceException;
import ru.edu.homework.springcrud.exception.db.DataSourceReadException;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;

import java.util.HashMap;
import java.util.Map;

@Component
@ConditionalOnProperty(name = "repository.type", havingValue = "file")
public class FileDataSource implements DataSource {

    DataFile<ProductEntity, String> productDataFile;

    DataFile<ProductCategoryEntity, Long> productCategoryDataFile;

    public FileDataSource(@Value("${product.category.repository.file.path:src/main/resources/products_category.dat}") String productCategoryFileName,
                          @Value("${product.repository.file.path:src/main/resources/products.dat}") String productFileName) {
        productDataFile = new DataFile<>(productFileName);
        productCategoryDataFile = new DataFile<>(productCategoryFileName);
    }


    public Map<String, Product> getProducts() {
        try {
            Map<String, ProductEntity> productEntities = productDataFile.load();
            Map<String, Product> products = new HashMap<>();
            for (ProductEntity productEntity : productEntities.values()) {
                products.put(productEntity.getPartNumber(), Product.builder()
                        .partNumber(productEntity.getPartNumber())
                        .name(productEntity.getName())
                        .price(productEntity.getPrice())
                        .quantity(productEntity.getQuantity())
                        .productCategory(new ProductCategory(productEntity.getProductCategoryId(), null, null, null))
                        .build());
            }
            return products;
        } catch (DataSourceReadException e) {
            return new HashMap<>();
        }
    }

    public Map<Long, ProductCategory> getProductCategories() {
        try {
            Map<Long, ProductCategoryEntity> productCategoryEntities = productCategoryDataFile.load();
            Map<Long, ProductCategory> productCategories = new HashMap<>();
            for (ProductCategoryEntity productCategoryEntity : productCategoryEntities.values()) {
                productCategories.put(productCategoryEntity.getId(), ProductCategory.builder()
                        .id(productCategoryEntity.getId())
                        .name(productCategoryEntity.getName())
                        .path(productCategoryEntity.getPath())
                        .build());
            }
            return productCategories;
        } catch (DataSourceReadException e) {
            return new HashMap<>();
        }
    }

    public void saveProducts(Map<String, Product> products) {
        try {
            Map<String, ProductEntity> productEntities = new HashMap<>();
            for (Product product : products.values()) {
                productEntities.put(product.getPartNumber(), ProductEntity.builder()
                        .partNumber(product.getPartNumber())
                        .name(product.getName())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .productCategoryId(product.getProductCategory().getId())
                        .build());
            }
            productDataFile.save(productEntities);
        } catch (DataSourceReadException e) {
            throw new DataSourceException("Error while saving products");
        }
    }

    public void saveProductCategories(Map<Long, ProductCategory> productCategories) {
        try {
            Map<Long, ProductCategoryEntity> productCategoryEntities = new HashMap<>();
            for (ProductCategory productCategory : productCategories.values()) {
                productCategoryEntities.put(productCategory.getId(), ProductCategoryEntity.builder()
                        .id(productCategory.getId())
                        .name(productCategory.getName())
                        .path(productCategory.getPath())
                        .build());
            }
            productCategoryDataFile.save(productCategoryEntities);
        } catch (DataSourceReadException e) {
            throw new DataSourceException("Error while saving product categories");
        }
    }


}
