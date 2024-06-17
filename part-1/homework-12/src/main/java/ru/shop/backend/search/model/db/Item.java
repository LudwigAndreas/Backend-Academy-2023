package ru.shop.backend.search.model.db;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Item {

    @Id
    @Column
    private Long itemId;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private Long price;

    @Column
    private String image;

    private String cat;

}
