package ru.shop.backend.search.core.item_search;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import ru.shop.backend.search.model.criteria.ItemSearchCriteria;

public interface ItemQueryBuilder {

    void createQuery(ItemSearchCriteria criteria);

    NativeSearchQuery getSearchQuery();

    Pageable getPageRequest();
}
