package ru.edu.homework.springcrud.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.edu.homework.springcrud.db.DataSource;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;
import ru.edu.homework.springcrud.repository.impl.ProductRepositoryImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryImplTest {

    @Mock
    private DataSource dataSource;

    private ProductRepositoryImpl repository;

    private ProductCategory defaultProductCategory;

    @BeforeEach
    public void setUp() {
        repository = new ProductRepositoryImpl(dataSource);
        defaultProductCategory = new ProductCategory(0L, "Default", "default", Collections.emptySet());
    }

    @Test
    public void testFindAll() {
        // Given
        List<Product> expectedProducts = Arrays.asList(new Product("456", "Product 2", 100, 3, defaultProductCategory), new Product("123", "Product 1", 100, 3, defaultProductCategory));
        when(dataSource.getProducts()).thenReturn(new HashMap<>(Map.of(expectedProducts.get(0).getPartNumber(), expectedProducts.get(0), expectedProducts.get(1).getPartNumber(), expectedProducts.get(1))));

        // When
        List<Product> actualProducts = repository.findAll();

        // Then
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testFindById() {
        // Given
        String partNumber = "123";
        Product expectedProduct = new Product("123", "Product 1", 100, 3, defaultProductCategory);
        when(dataSource.getProducts()).thenReturn(Collections.singletonMap(partNumber, expectedProduct));

        // When
        Optional<Product> actualProduct = repository.findById(partNumber);

        // Then
        assertTrue(actualProduct.isPresent());
        assertEquals(expectedProduct, actualProduct.get());
    }

    @Test
    public void testDelete() throws NotFoundException {
        // Given
        String partNumber = "123";
        Product productToDelete = new Product("123", "Product 1", 100, 3, defaultProductCategory);
        HashMap<String, Product> products = new HashMap<>();
        products.put(partNumber, productToDelete);
        when(dataSource.getProducts()).thenReturn(products);

        // When
        repository.delete(productToDelete);

        // Then
        verify(dataSource).saveProducts(Collections.emptyMap());
    }

    @Test
    public void testSave() throws NotFoundException {
        // Given
        String partNumber = "123";
        Product productToSave = new Product("123", "Product 1", 100, 3, defaultProductCategory);
        when(dataSource.getProducts()).thenReturn(new HashMap<>());
        when(dataSource.getProductCategories()).thenReturn(Collections.singletonMap(0L, defaultProductCategory));

        // When
        Product savedProduct = repository.save(productToSave);

        // Then
        assertEquals(productToSave, savedProduct);
        verify(dataSource).saveProducts(Collections.singletonMap(partNumber, savedProduct));
    }

    @Test
    public void testSaveAll() throws NotFoundException {
        // Given
        List<Product> productsToSave = Arrays.asList(
                new Product("456", "Product 2", 100, 3, defaultProductCategory),
                new Product("123", "Product 1", 100, 3, defaultProductCategory
                ));
        when(dataSource.getProducts()).thenReturn(new HashMap<>());
        when(dataSource.getProductCategories()).thenReturn(Collections.singletonMap(0L, defaultProductCategory));

        List<Product> savedProducts = repository.saveAll(productsToSave);

        assertEquals(productsToSave, savedProducts);
        verify(dataSource).saveProducts(new HashMap<>(Map.of(productsToSave.get(0).getPartNumber(), productsToSave.get(0), productsToSave.get(1).getPartNumber(), productsToSave.get(1))));
    }

    @Test
    public void testExistsById() {
        // Given
        String partNumber = "123";
        Product existingProduct = new Product("123", "Product 1", 100, 3, defaultProductCategory);
        when(dataSource.getProducts()).thenReturn(Collections.singletonMap(partNumber, existingProduct));

        // When
        boolean exists = repository.existsById(partNumber);

        // Then
        assertTrue(exists);
    }

    @Test
    public void testCount() {
        // Given
        int expectedCount = 2;
        HashMap<String, Product> products = new HashMap<>();
        products.put("123", new Product("123", "Product 1", 100, 3, defaultProductCategory));
        products.put("456", new Product("456", "Product 2", 100, 3, defaultProductCategory));
        when(dataSource.getProducts()).thenReturn(products);

        // When
        long actualCount = repository.count();

        // Then
        assertEquals(expectedCount, actualCount);
    }

}