package ru.edu.homework.springcrud.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import ru.edu.homework.springcrud.mapper.ProductMapper;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;
import ru.edu.homework.springcrud.service.ProductService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper modelMapper;

    @InjectMocks
    private ProductController productController;

    Product product1;
    Product product2;
    ProductDto productDto1;
    ProductDto productDto2;
    ProductCategory productCategory1;
    ProductCategory productCategory2;
    ProductCategoryDto productCategoryDto1;
    ProductCategoryDto productCategoryDto2;


    @BeforeEach
    void setUp() throws NotFoundException, AlreadyExistsException {
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

    @AfterEach
    void tearDown() {

    }

    @Test
    void getAllProducts_shouldReturnListOfProductDto() {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));
        when(modelMapper.toDto(product1)).thenReturn(productDto1);
        when(modelMapper.toDto(product2)).thenReturn(productDto2);
        List<ProductDto> result = productController.getAllProducts();

        verify(productService, times(1)).getAllProducts();
        verify(modelMapper, times(1)).toDto(product1);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(productDto1, result.get(0));
        Assertions.assertEquals(productDto2, result.get(1));


    }

    @Test
    void getProductById_shouldReturnProductDto() throws NotFoundException {
        when(productService.getProductById("ARTICUL")).thenReturn(product1);
        when(modelMapper.toDto(product1)).thenReturn(productDto1);
        ProductDto result = productController.getProductById("ARTICUL");

        verify(productService, times(1)).getProductById("ARTICUL");
        verify(modelMapper, times(1)).toDto(product1);

        Assertions.assertEquals(productDto1, result);
    }

    @Test
    void createProduct_shouldReturnProductDto() throws AlreadyExistsException, NotFoundException {
        when(productService.createProduct(product1)).thenReturn(product1);
        when(modelMapper.toEntity(productDto1)).thenReturn(product1);
        when(modelMapper.toDto(product1)).thenReturn(productDto1);

        ProductDto result = productController.createProduct(productDto1);

        verify(productService, times(1)).createProduct(product1);
        verify(modelMapper, times(1)).toEntity(productDto1);

        Assertions.assertEquals(productDto1, result);
    }

    @Test
    void updateProduct_shouldReturnProductDto() throws NotFoundException, AlreadyExistsException {
        when(productService.updateProduct("ARTICUL", product1)).thenReturn(product1);
        when(modelMapper.toEntity(productDto1)).thenReturn(product1);
        when(modelMapper.toDto(product1)).thenReturn(productDto1);

        ProductDto result = productController.updateProduct("ARTICUL", productDto1);

        verify(productService, times(1)).updateProduct("ARTICUL", product1);
        verify(modelMapper, times(1)).toEntity(productDto1);

        Assertions.assertEquals(productDto1, result);
    }

    @Test
    void deleteProduct_shouldCallDeleteProduct() throws NotFoundException {

        productController.deleteProduct("ARTICUL");

        verify(productService, times(1)).deleteProduct("ARTICUL");
    }
}
