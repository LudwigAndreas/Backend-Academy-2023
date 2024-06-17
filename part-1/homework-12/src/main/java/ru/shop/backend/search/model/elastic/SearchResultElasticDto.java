package ru.shop.backend.search.model.elastic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@ToString
@Getter
public class SearchResultElasticDto {
    public List<CatalogueElasticDto> result;
}
