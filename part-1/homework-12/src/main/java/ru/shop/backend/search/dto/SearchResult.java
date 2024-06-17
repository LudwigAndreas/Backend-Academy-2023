package ru.shop.backend.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.shop.backend.search.model.db.Item;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {

    private List<Item> items;

    private List<Category> categories;

    private List<TypeHelpText> typeQueries;

}
