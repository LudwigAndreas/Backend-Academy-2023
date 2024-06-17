package ru.edu.homework.springcrud.db.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.homework.springcrud.db.entity.ProductCategoryEntity;
import ru.edu.homework.springcrud.db.entity.ProductEntity;
import ru.edu.homework.springcrud.exception.db.DataSourceReadException;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileDataSourceTest {

    @Mock
    private DataFile<ProductEntity, String> productDataFile;

    @Mock
    private DataFile<ProductCategoryEntity, Long> productCategoryDataFile;

    @InjectMocks
    private FileDataSource fileDataSource = new FileDataSource("productCategoryTest.dat", "productTest.dat");

    @TempDir
    static Path tempDir;

    @Test
    void getProducts_shouldReturnProducts() throws DataSourceReadException {
        // Arrange
        Map<String, ProductEntity> productEntities = new HashMap<>();
        ProductEntity productEntity = new ProductEntity("123", "Product1", 10.0, 100, 1L);
        productEntities.put(productEntity.getPartNumber(), productEntity);

        when(productDataFile.load()).thenReturn(productEntities);

        // Act
        Map<String, Product> products = fileDataSource.getProducts();

        // Assert
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());

        Product product = products.get("123");
        assertNotNull(product);
        assertEquals("123", product.getPartNumber());
        assertEquals("Product1", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals(100, product.getQuantity());
        assertNotNull(product.getProductCategory());
        assertEquals(1L, product.getProductCategory().getId());
    }

    @Test
    void getProductCategories_shouldReturnProductCategories() throws DataSourceReadException {
        // Arrange
        Map<Long, ProductCategoryEntity> productCategoryEntities = new HashMap<>();
        ProductCategoryEntity categoryEntity = new ProductCategoryEntity(1L, "Category1", "/path");
        productCategoryEntities.put(categoryEntity.getId(), categoryEntity);

        when(productCategoryDataFile.load()).thenReturn(productCategoryEntities);

        // Act
        Map<Long, ProductCategory> productCategories = fileDataSource.getProductCategories();

        // Assert
        assertFalse(productCategories.isEmpty());
        assertEquals(1, productCategories.size());

        ProductCategory productCategory = productCategories.get(1L);
        assertNotNull(productCategory);
        assertEquals(1L, productCategory.getId());
        assertEquals("Category1", productCategory.getName());
        assertEquals("/path", productCategory.getPath());
    }

    @Test
    void saveProducts_shouldSaveProductsToFile() throws DataSourceReadException {
        // Arrange
        Map<String, Product> products = new HashMap<>();
        Product product = new Product("123", "Product1", 10.0, 100, new ProductCategory(1L, "Category1", "/path", new HashSet<>()));
        products.put(product.getPartNumber(), product);

        // Act
        fileDataSource.saveProducts(products);

        // Assert
        verify(productDataFile, times(1)).save(anyMap());
    }

    @Test
    void saveProductCategories_shouldSaveProductCategoriesToFile() throws DataSourceReadException {
        // Arrange
        Map<Long, ProductCategory> productCategories = new HashMap<>();
        ProductCategory category = new ProductCategory(1L, "Category1", "/path", new HashSet<>());
        productCategories.put(category.getId(), category);

        // Act
        fileDataSource.saveProductCategories(productCategories);

        // Assert
        verify(productCategoryDataFile, times(1)).save(anyMap());
    }
}
