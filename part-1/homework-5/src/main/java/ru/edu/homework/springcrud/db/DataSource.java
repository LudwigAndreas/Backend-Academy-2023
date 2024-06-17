package ru.edu.homework.springcrud.db;

import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;

import java.util.List;
import java.util.Map;

public interface DataSource {

    Map<String, Product> getProducts();

    Map<Long, ProductCategory> getProductCategories();

    void saveProducts(Map<String, Product> products);

    void saveProductCategories(Map<Long, ProductCategory> productCategories);

}
