package ru.edu.homework.springcrud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategoryDto {

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id;

        @NotNull(message = "Id should not be null")
        @Pattern(regexp = "^[a-zA-Z0-9 ]{4,}$", message = "Id should contain only letters, digits, and spaces and be at least 4 characters long")
        String name;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String path;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Set<ProductDto> products;

        public void setName(String name) {
                this.name = name;
                this.path = name.toLowerCase().replace(' ', '_');
        }
}
