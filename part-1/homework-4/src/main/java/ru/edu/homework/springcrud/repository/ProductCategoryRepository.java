package ru.edu.homework.springcrud.repository;

import ru.edu.homework.springcrud.model.ProductCategory;

import java.util.Optional;

public interface ProductCategoryRepository extends EducationalRepository<ProductCategory, Long> {

    Optional<ProductCategory> findByUrl(String url);
}
