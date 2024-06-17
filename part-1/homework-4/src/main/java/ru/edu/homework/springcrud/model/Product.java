package ru.edu.homework.springcrud.model;

import lombok.*;
import ru.edu.homework.springcrud.db.annotation.IdField;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @IdField
    String partNumber;

    String name;

    double price;

    int quantity;

    ProductCategory productCategory;

}
