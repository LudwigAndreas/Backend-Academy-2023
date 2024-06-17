package ru.shop.backend.search.model.db;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ItemEntity {
    @Id
    private long itemId;

    private String name;

    private long brandId;

    private String brand;

    private String type;

    private long catalogueId;

    private String catalogue;

    private String description;

}
