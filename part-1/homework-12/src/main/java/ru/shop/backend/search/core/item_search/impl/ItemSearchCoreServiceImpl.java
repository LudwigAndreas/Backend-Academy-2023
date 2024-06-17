package ru.shop.backend.search.core.item_search.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Component;
import ru.shop.backend.search.core.item_search.ItemQueryBuilder;
import ru.shop.backend.search.model.criteria.ItemSearchCriteria;
import ru.shop.backend.search.core.item_search.ItemSearchCoreService;
import ru.shop.backend.search.model.elastic.ItemDocument;

@Component
public class ItemSearchCoreServiceImpl implements ItemSearchCoreService {

    private final ItemQueryBuilder queryBuilder;

    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public ItemSearchCoreServiceImpl(ItemQueryBuilder queryBuilder, ElasticsearchOperations elasticsearchOperations) {
        this.queryBuilder = queryBuilder;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public SearchPage<ItemDocument> search(ItemSearchCriteria criteria) {
        queryBuilder.createQuery(criteria);
        NativeSearchQuery query = queryBuilder.getSearchQuery();

        SearchHits<ItemDocument> searchHits = elasticsearchOperations.search(
                query,
                ItemDocument.class
        );

        SearchPage<ItemDocument> searchPage = SearchHitSupport.searchPageFor(
                searchHits,
                queryBuilder.getPageRequest()
        );

        return searchPage;
    }

    @Override
    public String getRawJsonQuery(ItemSearchCriteria criteria) {
        queryBuilder.createQuery(criteria);
        NativeSearchQuery query = queryBuilder.getSearchQuery();

        return query.getQuery().toString();
    }
}
