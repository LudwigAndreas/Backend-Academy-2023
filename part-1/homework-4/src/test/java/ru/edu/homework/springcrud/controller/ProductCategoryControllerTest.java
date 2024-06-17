package ru.edu.homework.springcrud.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.edu.homework.springcrud.dto.ProductCategoryDto;
import ru.edu.homework.springcrud.dto.ProductDto;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.mapper.ProductCategoryMapper;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;
import ru.edu.homework.springcrud.service.ProductCategoryService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCategoryControllerTest {

    @Mock
    private ProductCategoryService categoryService;

    @Mock
    private ProductCategoryMapper modelMapper;

    @InjectMocks
    private ProductCategoryController categoryController;

    Product product1;
    Product product2;
    ProductDto productDto1;
    ProductDto productDto2;
    ProductCategory productCategory1;
    ProductCategory productCategory2;
    ProductCategoryDto productCategoryDto1;
    ProductCategoryDto productCategoryDto2;

    @BeforeEach
    void setUp() {
        product1 = new Product("ARTICUL", "Some name", 100, 1, null);
        product2 = new Product("ARTICUL2", "Some name2", 200, 2, null);
        productCategory1 = new ProductCategory(1L, "Some category", "some_category", new HashSet<>(List.of(product1, product2)));
        productCategory2 = new ProductCategory(2L, "Some category2", "some_category2", new HashSet<>());
        product1.setProductCategory(productCategory1);
        product2.setProductCategory(productCategory1);

        productDto1 = new ProductDto("ARTICUL", "Some name", 100, 1, null);
        productDto2 = new ProductDto("ARTICUL2", "Some name2", 200, 2, null);
        productCategoryDto1 = new ProductCategoryDto(1L, "Some category", "some_category", new HashSet<>(List.of(productDto1, productDto2)));
        productCategoryDto2 = new ProductCategoryDto(2L, "Some category2", "some_category2", new HashSet<>());
        productDto1.setCategoryId(productCategoryDto1.getId());
        productDto2.setCategoryId(productCategoryDto1.getId());
    }

    @Test
    void getAllCategories_shouldReturnListOfProductCategoryDto() {
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(productCategory1, productCategory2));
        when(modelMapper.toDto(productCategory2)).thenReturn(productCategoryDto2);
        when(modelMapper.toDto(productCategory1)).thenReturn(productCategoryDto1);

        List<ProductCategoryDto> result = categoryController.getAllCategories();

        assertEquals(2, result.size());
        assertEquals(productCategoryDto1, result.get(0));
        assertEquals(productCategoryDto2, result.get(1));

        verify(categoryService, times(1)).getAllCategories();
        verify(modelMapper, times(1)).toDto(productCategory1);
        verify(modelMapper, times(1)).toDto(productCategory2);

    }

    @Test
    void getCategoryById_shouldReturnProductCategoryDto() throws NotFoundException {
        Long categoryId = 1L;
        when(categoryService.getCategoryById(categoryId)).thenReturn(productCategory1);
        when(modelMapper.toDto(productCategory1)).thenReturn(productCategoryDto1);

        ProductCategoryDto result = categoryController.getCategoryById(categoryId);

        assertEquals(productCategoryDto1, result);
        verify(categoryService, times(1)).getCategoryById(categoryId);
        verify(modelMapper, times(1)).toDto(productCategory1);
    }

    @Test
    void createCategory_shouldReturnProductCategoryDto() throws AlreadyExistsException, NotFoundException {
        ProductCategoryDto categoryDto = productCategoryDto1;
        when(categoryService.createCategory(productCategory1)).thenReturn(productCategory1);
        when(modelMapper.toEntity(categoryDto)).thenReturn(productCategory1);
        when(modelMapper.toDto(productCategory1)).thenReturn(categoryDto);

        ProductCategoryDto result = categoryController.createCategory(categoryDto);

        assertEquals(categoryDto, result);
        verify(categoryService, times(1)).createCategory(productCategory1);
        verify(modelMapper, times(1)).toEntity(categoryDto);
        verify(modelMapper, times(1)).toDto(productCategory1);
    }

    @Test
    void updateCategory_shouldReturnProductCategoryDto() throws NotFoundException, AlreadyExistsException {
        Long categoryId = 1L;
        ProductCategoryDto categoryDto = productCategoryDto1;

        when(categoryService.updateCategory(categoryId, productCategory1)).thenReturn(productCategory1);
        when(modelMapper.toEntity(categoryDto)).thenReturn(productCategory1);
        when(modelMapper.toDto(productCategory1)).thenReturn(categoryDto);

        ProductCategoryDto result = categoryController.updateCategory(categoryId, categoryDto);

        assertEquals(categoryDto, result);
        verify(categoryService, times(1)).updateCategory(categoryId, productCategory1);
        verify(modelMapper, times(1)).toDto(productCategory1);

    }

    @Test
    void deleteCategory_shouldCallDeleteCategory() throws NotFoundException {
        Long categoryId = 1L;

        categoryController.deleteCategory(categoryId);

        verify(categoryService, times(1)).deleteCategory(categoryId);
    }
}
