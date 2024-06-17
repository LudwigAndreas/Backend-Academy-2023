package ru.edu.homework.springcrud.mapper;

import org.springframework.stereotype.Component;
import ru.edu.homework.springcrud.dto.ProductCategoryDto;
import ru.edu.homework.springcrud.dto.ProductDto;
import ru.edu.homework.springcrud.model.ProductCategory;

import java.util.stream.Collectors;

@Component
public class ProductCategoryMapper {

    ProductMapper productMapper;

    public ProductCategoryMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public ProductCategoryDto toDto(ProductCategory productCategory) {
        return ProductCategoryDto.builder()
                .id(productCategory.getId())
                .name(productCategory.getName())
                .path(productCategory.getPath())
                .products(productCategory.getProducts().stream()
                        .map(productMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public ProductCategory toEntity(ProductCategoryDto productCategoryDto) {
        return ProductCategory.builder()
                .name(productCategoryDto.getName())
                .path(productCategoryDto.getPath())
                .build();
    }
}
