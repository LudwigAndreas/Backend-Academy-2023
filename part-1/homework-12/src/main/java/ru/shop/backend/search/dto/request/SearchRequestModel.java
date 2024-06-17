package ru.shop.backend.search.dto.request;


import lombok.Data;

@Data
public class SearchRequestModel {

    private Integer page = 1;

    private Integer size = 10;

    private Integer regionId;

    private String term;

}
