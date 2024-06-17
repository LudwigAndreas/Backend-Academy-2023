package ru.edu.homework.springcrud.repository;

import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends EducationalRepository<Product, String> {

}
