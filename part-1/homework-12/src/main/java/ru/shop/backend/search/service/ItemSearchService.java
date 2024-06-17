package ru.shop.backend.search.service;

import org.springframework.data.domain.Pageable;
import ru.shop.backend.search.dto.SearchResult;
import ru.shop.backend.search.dto.request.SearchRequestModel;
import ru.shop.backend.search.model.elastic.CatalogueElasticDto;

import java.util.List;

public interface ItemSearchService {

//    SearchResult getSearchResult(Integer regionId, String text);
//
//    List<CatalogueElasticDto> getAll(String text);
//
//    List<CatalogueElasticDto> getAll(String text, Pageable pageable);
//    List<CatalogueElasticDto> getByName(String num);
//
//    List<CatalogueElasticDto> getByItemId(String itemId);
//
//    List<CatalogueElasticDto> getAllFull(String text);

    SearchResult search(SearchRequestModel searchRequestModel);

    String getRawJsonQuery(SearchRequestModel searchRequestModel);

}
