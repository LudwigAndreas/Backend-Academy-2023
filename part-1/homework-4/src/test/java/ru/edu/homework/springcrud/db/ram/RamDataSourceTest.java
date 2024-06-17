package ru.edu.homework.springcrud.db.ram;

import org.junit.jupiter.api.Test;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RamDataSourceTest {

    @Test
    void saveProducts_shouldSaveProducts() {
        // Arrange
        RamDataSource ramDataSource = new RamDataSource();
        Map<String, Product> products = new HashMap<>();
        Product product1 = new Product("123", "Product1", 10.0, 100, new ProductCategory(1L, "Category1", "/path", null));
        Product product2 = new Product("456", "Product2", 15.0, 50, new ProductCategory(2L, "Category2", "/path2", null));
        products.put(product1.getPartNumber(), product1);
        products.put(product2.getPartNumber(), product2);

        // Act
        ramDataSource.saveProducts(products);

        // Assert
        assertEquals(products, ramDataSource.getProducts());
    }

    @Test
    void saveProductCategories_shouldSaveProductCategories() {
        // Arrange
        RamDataSource ramDataSource = new RamDataSource();
        Map<Long, ProductCategory> productCategories = new HashMap<>();
        ProductCategory category1 = new ProductCategory(1L, "Category1", "/path", null);
        ProductCategory category2 = new ProductCategory(2L, "Category2", "/path2", null);
        productCategories.put(category1.getId(), category1);
        productCategories.put(category2.getId(), category2);

        // Act
        ramDataSource.saveProductCategories(productCategories);

        // Assert
        assertEquals(productCategories, ramDataSource.getProductCategories());
    }
}
