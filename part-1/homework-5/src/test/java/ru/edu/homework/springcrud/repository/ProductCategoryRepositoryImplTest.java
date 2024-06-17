package ru.edu.homework.springcrud.repository;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.homework.springcrud.db.DataSource;
import ru.edu.homework.springcrud.db.ProductCategoryIdGenerator;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.ProductCategory;
import ru.edu.homework.springcrud.repository.impl.ProductCategoryRepositoryImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class ProductCategoryRepositoryImplTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private ProductCategoryIdGenerator productCategoryIdGenerator;

    @InjectMocks
    private ProductCategoryRepositoryImpl repository;

    @Test
    public void testFindAll() {
        // Given
        List<ProductCategory> productCategories = Arrays.asList(
                new ProductCategory(null, "Category 1", "category-1", new HashSet<>()),
                new ProductCategory(null, "Category 2", "category-2", new HashSet<>())
        );
        when(dataSource.getProductCategories()).thenReturn(new HashMap<Long, ProductCategory>() {{
            put(1L, productCategories.get(0));
            put(2L, productCategories.get(1));
        }});

        // When
        List<ProductCategory> actualProductCategories = repository.findAll();

        // Then
        assertEquals(productCategories, actualProductCategories);
    }

    @Test
    public void testFindById() {
        // Given

        Long id = 1L;
        Map<Long, ProductCategory> expectedProductCategories = new HashMap<>();
        expectedProductCategories.put(id, new ProductCategory(id, "Category 1", "category-1", new HashSet<>()));
        when(dataSource.getProductCategories()).thenReturn(expectedProductCategories);

        // When
        Optional<ProductCategory> actualProductCategory = repository.findById(id);

        // Then
        assertTrue(actualProductCategory.isPresent());
        assertEquals(expectedProductCategories.get(id), actualProductCategory.get());
    }

    @Test
    public void testDelete() throws NotFoundException {
        // Given
        Long id = 1L;
        Map<Long, ProductCategory> expectedProductCategories = new HashMap<>();
        expectedProductCategories.put(id, new ProductCategory(id, "Category 1", "category-1", new HashSet<>()));
        when(dataSource.getProductCategories()).thenReturn(expectedProductCategories);

        // When
        repository.delete(expectedProductCategories.get(id));

        // Then
        verify(dataSource).saveProductCategories(new HashMap<>());
    }

    @Test
    public void testDeleteById() throws NotFoundException {
        // Given
        Long id = 1L;
        Map<Long, ProductCategory> expectedProductCategories = new HashMap<>();
        expectedProductCategories.put(id, new ProductCategory(id, "Category 1", "category-1", new HashSet<>()));
        when(dataSource.getProductCategories()).thenReturn(expectedProductCategories);

        // When
        repository.deleteById(id);

        // Then
        verify(dataSource).saveProductCategories(new HashMap<>());
    }

    @Test
    public void testSave() throws AlreadyExistsException {
        // Given
        ProductCategory productCategory = new ProductCategory(null, "Category 1", "category-1", new HashSet<>());
        when(dataSource.getProductCategories()).thenReturn(new HashMap<>());

        // When
        ProductCategory actualProductCategory = repository.save(productCategory);

        // Then
        assertEquals(1L, actualProductCategory.getId());
        assertEquals("Category 1", actualProductCategory.getName());
        assertEquals("category-1", actualProductCategory.getPath());
        verify(dataSource).saveProductCategories(Collections.singletonMap(1L, actualProductCategory));
    }

    @Test
    public void testSaveAll() throws AlreadyExistsException {
        // Given
        List<ProductCategory> productCategories = Arrays.asList(
                new ProductCategory(null, "Category 1", "category-1", new HashSet<>()),
                new ProductCategory(null, "Category 2", "category-2", new HashSet<>())
        );
        when(dataSource.getProductCategories()).thenReturn(new HashMap<>());

        // When
        List<ProductCategory> actualProductCategories = repository.saveAll(productCategories);

        // Then
        assertEquals(2, actualProductCategories.size());
        assertEquals(1L, actualProductCategories.get(0).getId());
        assertEquals("Category 1", actualProductCategories.get(0).getName());
        assertEquals("category-1", actualProductCategories.get(0).getPath());
        assertEquals(2L, actualProductCategories.get(1).getId());
        assertEquals("Category 2", actualProductCategories.get(1).getName());
        assertEquals("category-2", actualProductCategories.get(1).getPath());
        verify(dataSource).saveProductCategories(new HashMap<Long, ProductCategory>() {{
            put(1L, actualProductCategories.get(0));
            put(2L, actualProductCategories.get(1));
        }});
    }

    @Test
    public void testExistsById() {
        // Given
        Long id = 1L;
        ProductCategory productCategory = new ProductCategory(id, "Category 1", "category-1", new HashSet<>());
        when(dataSource.getProductCategories()).thenReturn(Collections.singletonMap(id, productCategory));

        // When
        boolean actualResult = repository.existsById(id);

        // Then
        assertTrue(actualResult);
    }
}