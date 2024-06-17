package ru.shop.backend.search.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.shop.backend.search.dto.SearchResult;
import ru.shop.backend.search.model.elastic.SearchResultElasticDto;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

public interface SearchApi {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращает результаты поиска для всплывающего окна",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SearchResult.class))}),
            @ApiResponse(responseCode = "400", description = "Ошибка обработки",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Регион не найден",
                    content = @Content)})
    @Parameter(name = "text", description = "Поисковый запрос")
    @GetMapping
    SearchResult findItemsInRegion(@RequestParam String text, @CookieValue(name="regionId", defaultValue="1") int regionId);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращает результаты поиска",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SearchResult.class))}),
            @ApiResponse(responseCode = "400", description = "Ошибка обработки",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Регион не найден",
                    content = @Content)})
    @Parameter(name = "text", description = "Поисковый запрос")
    @RequestMapping(method = GET, value = "/by", produces = "application/json;charset=UTF-8")
    ResponseEntity<SearchResultElasticDto> findItems(@RequestParam String text, @CookieValue(name="regionId", defaultValue="1") int regionId);

}
