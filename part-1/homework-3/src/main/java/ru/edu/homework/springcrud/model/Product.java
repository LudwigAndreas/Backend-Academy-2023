package ru.edu.homework.springcrud.model;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product implements Serializable {

    String partNumber;

    String name;

    double price;

    int quantity;

}
