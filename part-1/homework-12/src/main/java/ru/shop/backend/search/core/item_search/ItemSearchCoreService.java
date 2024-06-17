package ru.shop.backend.search.core.item_search;

import org.springframework.data.elasticsearch.core.SearchPage;
import ru.shop.backend.search.model.criteria.ItemSearchCriteria;
import ru.shop.backend.search.model.elastic.ItemDocument;

public interface ItemSearchCoreService {

    SearchPage<ItemDocument> search(ItemSearchCriteria criteria);

    String getRawJsonQuery(ItemSearchCriteria criteria);
}
