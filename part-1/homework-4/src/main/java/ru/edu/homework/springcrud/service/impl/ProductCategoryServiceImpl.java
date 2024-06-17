package ru.edu.homework.springcrud.service.impl;

import org.springframework.stereotype.Service;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.ProductCategory;
import ru.edu.homework.springcrud.repository.ProductCategoryRepository;
import ru.edu.homework.springcrud.service.ProductCategoryService;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }


    @Override
    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory getCategoryById(Long id) throws NotFoundException {
        return productCategoryRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public ProductCategory getCategoryByUrl(String url) throws NotFoundException {
        return null;
    }

    @Override
    public ProductCategory createCategory(ProductCategory category) throws AlreadyExistsException, NotFoundException {
        if (productCategoryRepository.findById(category.getId()).isPresent()) {
            throw new AlreadyExistsException("Category with id " + category.getId() + " already exists");
        }
        return productCategoryRepository.save(category);
    }

    @Override
    public ProductCategory updateCategory(Long id, ProductCategory category) throws NotFoundException, AlreadyExistsException {
        if (productCategoryRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Category with id " + id + " not found");
        }
        category.setId(id);
        return productCategoryRepository.save(category);
    }

    @Override
    public ProductCategory updateCategoryByUrl(String url, ProductCategory category) throws NotFoundException {
        return null;
    }

    @Override
    public void deleteCategory(Long id) throws NotFoundException {
        if (productCategoryRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Category with id " + id + " not found");
        }
        productCategoryRepository.deleteById(id);
    }

    @Override
    public void deleteCategoryByUrl(String url) throws NotFoundException {

    }
}
