package ru.edu.homework.springcrud.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.edu.homework.springcrud.db.annotation.IdField;

import java.io.Serializable;


/*
 * I'm using entity here as analog of sql table
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEntity  implements Serializable {
    @IdField
    String partNumber;
    String name;
    Double price;
    Integer quantity;
    Long productCategoryId;
}
