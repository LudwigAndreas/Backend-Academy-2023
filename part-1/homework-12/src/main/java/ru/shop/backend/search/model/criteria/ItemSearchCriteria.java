package ru.shop.backend.search.model.criteria;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchCriteria implements SearchCriteria {

    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 150;

    private Integer page = 1;

    private Integer size = 10;

    private String term;

    private Integer regionId;

}
