package ru.edu.homework.springcrud.service.impl;

import org.springframework.stereotype.Service;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.repository.ProductRepository;
import ru.edu.homework.springcrud.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String partNumber) throws NotFoundException {
        return productRepository.findById(partNumber).orElseThrow(NotFoundException::new);
    }

    @Override
    public Product createProduct(Product product) throws AlreadyExistsException {
        if (productRepository.findById(product.getPartNumber()).isPresent()) {
            throw new AlreadyExistsException("Product with partNumber " + product.getPartNumber() + " already exists");
        } else if (product.getPartNumber() == null || product.getPartNumber().isEmpty()) {
            throw new IllegalArgumentException("Product part number is null or empty");
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String partNumber, Product product) throws NotFoundException {
        if (productRepository.findById(partNumber).isEmpty()) {
            throw new NotFoundException("Product with partNumber " + partNumber + " not found");
        }
        product.setPartNumber(partNumber);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(String partNumber) throws NotFoundException {
        if (productRepository.findById(partNumber).isEmpty()) {
            throw new NotFoundException("Product with partNumber " + partNumber + " not found");
        }
        productRepository.delete(partNumber);
    }
}
