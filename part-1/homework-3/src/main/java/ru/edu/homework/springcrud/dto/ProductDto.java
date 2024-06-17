package ru.edu.homework.springcrud.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    @NotNull(message = "PartNumber should not be null")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "PartNumber should contain only uppercase letters and digits")
    String partNumber;

    @NotNull(message = "Name should not be null")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Name should contain only letters, digits, and spaces")
    String name;

    @Min(value = 0, message = "Price should be a non-negative number")
    double price;

    @Min(value = 0, message = "Price should be a non-negative number")
    int quantity;
}
