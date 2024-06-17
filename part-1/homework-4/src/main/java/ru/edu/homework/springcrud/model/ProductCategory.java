package ru.edu.homework.springcrud.model;

import lombok.*;
import ru.edu.homework.springcrud.db.annotation.IdField;
import ru.edu.homework.springcrud.db.annotation.UniqueField;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategory {

    @IdField
    Long id;

    @UniqueField
    String name;

    @UniqueField
    String path;

    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<Product> products = new HashSet<>();

}
