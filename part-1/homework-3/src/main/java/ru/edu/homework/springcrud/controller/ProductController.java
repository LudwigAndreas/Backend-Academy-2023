package ru.edu.homework.springcrud.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.edu.homework.springcrud.dto.ProductDto;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;

    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/product")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/product/{partNumber}")
    public ProductDto getProductById(@PathVariable("partNumber") String partNumber) throws NotFoundException {
        return toDto(productService.getProductById(partNumber));
    }

    @PostMapping("/product")
    public ProductDto createProduct(@RequestBody @Valid ProductDto product) throws AlreadyExistsException {
        return toDto(productService.createProduct(toModel(product)));
    }

    @PutMapping("/product/{partNumber}")
    public ProductDto updateProduct(@PathVariable("partNumber") String partNumber, @RequestBody @Valid ProductDto product) throws NotFoundException {
        return toDto(productService.updateProduct(partNumber, toModel(product)));
    }

    @DeleteMapping("/product/{partNumber}")
    public void deleteProduct(@PathVariable("partNumber") String partNumber) throws NotFoundException {
        productService.deleteProduct(partNumber);
    }

    private ProductDto toDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    private Product toModel(ProductDto productDto) {
        return modelMapper.map(productDto, Product.class);
    }

}
