package ru.shop.backend.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shop.backend.search.api.SearchApi;
import ru.shop.backend.search.dto.SearchResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.shop.backend.search.dto.request.SearchRequestModel;
import ru.shop.backend.search.model.elastic.SearchResultElasticDto;
import ru.shop.backend.search.repository.ItemDbRepository;
import ru.shop.backend.search.service.impl.ItemSearchServiceImpl;
import ru.shop.backend.search.service.ItemSearchService;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/api/search")
@Tag(name = "Поиск", description = "Методы поиска")
public class SearchController implements SearchApi {

//    private final ItemDbRepository itemDbRepository;
    private final ItemSearchService service;

    @Autowired
    public SearchController(ItemDbRepository itemDbRepository, ItemSearchServiceImpl service) {
//        this.itemDbRepository = itemDbRepository;
        this.service = service;
    }

    @GetMapping
    public SearchResult findItemsInRegion(@RequestParam String text, @CookieValue(name="regionId", defaultValue="1") int regionId){
//        return service.getSearchResult( regionId,  text);
        SearchRequestModel searchRequestModel = new SearchRequestModel();
        searchRequestModel.setTerm(text);
        searchRequestModel.setRegionId(regionId);
        return service.search(searchRequestModel);
    }

    @RequestMapping(method = GET, value = "/by", produces = "application/json;charset=UTF-8")
    public ResponseEntity<SearchResultElasticDto> findItems(@RequestParam String text, @CookieValue(name="regionId", defaultValue="1") int regionId) {
//        if (ItemSearchServiceImpl.isNumeric(text)) {
//            Integer itemId = itemDbRepository.findAllItemsBySku(text).stream().findFirst().orElse(null);
//            if (itemId == null) {
//                var catalogue = service.getByName(text);
//                if (!catalogue.isEmpty()) {
//                    return ResponseEntity.ok().body(new SearchResultElasticDto(catalogue));
//                }
//                return ResponseEntity.ok().body(new SearchResultElasticDto(service.getAllFull(text)));
//            }
//            try {
//                return ResponseEntity.ok().body(new SearchResultElasticDto(service.getByItemId(itemId.toString())));
//            } catch (Exception e) {
//            }
//        }
//        return ResponseEntity.ok().body(new SearchResultElasticDto(service.getAllFull(text)));
        return null;
    }

    @GetMapping("/raw")
    public String getRawJsonQuery(@RequestParam String text, @CookieValue(name="regionId", defaultValue="1") int regionId) {
        SearchRequestModel searchRequestModel = new SearchRequestModel();
        searchRequestModel.setTerm(text);
        searchRequestModel.setRegionId(regionId);
        return service.getRawJsonQuery(searchRequestModel);
    }
}
