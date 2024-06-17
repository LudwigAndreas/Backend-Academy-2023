package ru.edu.homework.springcrud.mapper;

import org.springframework.stereotype.Component;
import ru.edu.homework.springcrud.dto.ProductDto;
import ru.edu.homework.springcrud.model.Product;
import ru.edu.homework.springcrud.model.ProductCategory;

@Component
public class ProductMapper {
    public ProductDto toDto(Product user) {
        return ProductDto.builder()
                .partNumber(user.getPartNumber())
                .name(user.getName())
                .price(user.getPrice())
                .quantity(user.getQuantity())
                .categoryId(user.getProductCategory().getId())
                .build();
    }

    public Product toEntity(ProductDto userDto) {
        return Product.builder()
                .partNumber(userDto.getPartNumber())
                .name(userDto.getName())
                .price(userDto.getPrice())
                .quantity(userDto.getQuantity())
                .productCategory(ProductCategory.builder().id(userDto.getCategoryId()).build())
                .build();
    }
}
