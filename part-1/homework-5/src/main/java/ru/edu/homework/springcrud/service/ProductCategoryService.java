package ru.edu.homework.springcrud.service;

import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> getAllCategories();

    ProductCategory getCategoryById(Long id) throws NotFoundException;

    ProductCategory getCategoryByUrl(String url) throws NotFoundException;

    ProductCategory createCategory(ProductCategory category) throws AlreadyExistsException, NotFoundException;

    ProductCategory updateCategory(Long id, ProductCategory category) throws NotFoundException, AlreadyExistsException;

    ProductCategory updateCategoryByUrl(String url, ProductCategory category) throws NotFoundException;

    void deleteCategory(Long id) throws NotFoundException;

    void deleteCategoryByUrl(String url) throws NotFoundException;

    void moveProducts(Long sourceCategoryId, Long targetCategoryId) throws NotFoundException, AlreadyExistsException;

}
