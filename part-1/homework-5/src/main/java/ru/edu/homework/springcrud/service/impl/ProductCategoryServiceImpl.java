package ru.edu.homework.springcrud.service.impl;

import org.springframework.stereotype.Service;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;
import ru.edu.homework.springcrud.repository.ProductCategoryRepository;
import ru.edu.homework.springcrud.service.ProductCategoryService;

import java.util.List;
import java.util.Set;

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

    @Override
    public void moveProducts(Long sourceCategoryId, Long targetCategoryId) throws NotFoundException, AlreadyExistsException {
        ProductCategory sourceCategory = productCategoryRepository.findById(sourceCategoryId).orElseThrow(NotFoundException::new);
        ProductCategory targetCategory = productCategoryRepository.findById(targetCategoryId).orElseThrow(NotFoundException::new);
        if (sourceCategory.getId().equals(targetCategory.getId())) {
//            TODO: add custom exception
            throw new AlreadyExistsException("Source and target categories are the same");
        }
        Set<Product> products = Set.copyOf(sourceCategory.getProducts());
        sourceCategory.getProducts().removeAll(products);
        productCategoryRepository.save(sourceCategory);
        products.forEach(product -> product.setProductCategory(targetCategory));
        targetCategory.getProducts().addAll(products);
        productCategoryRepository.save(targetCategory);

    }
}
