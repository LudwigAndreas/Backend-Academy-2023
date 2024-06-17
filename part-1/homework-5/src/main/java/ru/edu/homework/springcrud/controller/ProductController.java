package ru.edu.homework.springcrud.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.edu.homework.springcrud.dto.ProductDto;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.mapper.ProductMapper;
import ru.edu.homework.springcrud.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;

    private final ProductMapper modelMapper;

    public ProductController(ProductService productService, ProductMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/product")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/product/{partNumber}")
    public ProductDto getProductById(@PathVariable("partNumber") String partNumber) throws NotFoundException {
        return modelMapper.toDto(productService.getProductById(partNumber));
    }

    @PostMapping("/product")
    public ProductDto createProduct(@RequestBody @Valid ProductDto product) throws AlreadyExistsException, NotFoundException {
        return modelMapper.toDto(productService.createProduct(modelMapper.toEntity(product)));
    }

    @PutMapping("/product/{partNumber}")
    public ProductDto updateProduct(@PathVariable("partNumber") String partNumber, @RequestBody @Valid ProductDto product) throws NotFoundException, AlreadyExistsException {
        return modelMapper.toDto(productService.updateProduct(partNumber, modelMapper.toEntity(product)));
    }

    @DeleteMapping("/product/{partNumber}")
    public void deleteProduct(@PathVariable("partNumber") String partNumber) throws NotFoundException {
        productService.deleteProduct(partNumber);
    }

}
