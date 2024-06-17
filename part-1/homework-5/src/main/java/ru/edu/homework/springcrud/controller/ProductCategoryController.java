package ru.edu.homework.springcrud.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.edu.homework.springcrud.dto.ProductCategoryDto;
import ru.edu.homework.springcrud.exception.repository.AlreadyExistsException;
import ru.edu.homework.springcrud.exception.repository.NotFoundException;
import ru.edu.homework.springcrud.mapper.ProductCategoryMapper;
import ru.edu.homework.springcrud.service.ProductCategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductCategoryController {

    ProductCategoryService categoryService;

    ProductCategoryMapper modelMapper;

    public ProductCategoryController(ProductCategoryService categoryService, ProductCategoryMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/category")
    public List<ProductCategoryDto> getAllCategories() {
        return categoryService.getAllCategories().stream()
                .map(modelMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{id}")
    public ProductCategoryDto getCategoryById(@PathVariable("id") Long id) throws NotFoundException {
        return modelMapper.toDto(categoryService.getCategoryById(id));
    }

    @PostMapping("/category")
    public ProductCategoryDto createCategory(@RequestBody @Valid ProductCategoryDto category) throws AlreadyExistsException, NotFoundException {
        return modelMapper.toDto(categoryService.createCategory(modelMapper.toEntity(category)));
    }

    @PutMapping("/category/{id}")
    public ProductCategoryDto updateCategory(@PathVariable("id") Long id, @RequestBody @Valid ProductCategoryDto category) throws NotFoundException, AlreadyExistsException {
        return modelMapper.toDto(categoryService.updateCategory(id, modelMapper.toEntity(category)));
    }

    @DeleteMapping("/category/{id}")
    public void deleteCategory(@PathVariable("id") Long id) throws NotFoundException {
        categoryService.deleteCategory(id);
    }

}
