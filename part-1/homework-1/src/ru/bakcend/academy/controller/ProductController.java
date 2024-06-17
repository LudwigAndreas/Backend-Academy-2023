package ru.bakcend.academy.controller;

import ru.bakcend.academy.exception.product.NotFoundException;
import ru.bakcend.academy.exception.product.ProductNotUniqueException;
import ru.bakcend.academy.model.Product;
import ru.bakcend.academy.service.ProductService;
import ru.bakcend.academy.view.ProductTableView;

import java.util.List;

public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public String createProduct(String partNumber, String name, Double cost, Integer number) {

        Product product = null;
        try {
            product = productService.saveProduct(partNumber, name, cost, number);
        } catch (ProductNotUniqueException e) {
            return "Save error: " + e.getMessage() + "\n";
        }
        return "Product Saved\n" + ProductTableView.productView(product);
    }

    public String getAllProducts() {
        List<Product> productList = productService.readAllProducts();

        return ProductTableView.productsView(productList);
    }

    public String updateProduct(String partNumber, String name, Double cost, Integer number) {
        Product product = null;
        try {
            product = productService.updateProduct(partNumber, name, cost, number);
        } catch (NotFoundException e) {
            return "Update error: " + e.getMessage() + "\n";
        }
        return "Product updated\n" + ProductTableView.productView(product);
    }

    public String deleteProduct(String partNumber) {
        try {
            productService.deleteProduct(partNumber);
        } catch (NotFoundException e) {
            return "Delete error: " + e.getMessage() + "\n";
        }
        return "Product " + partNumber + " deleted\n";
    }


}
