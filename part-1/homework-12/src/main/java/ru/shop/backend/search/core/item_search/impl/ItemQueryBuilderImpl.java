package ru.shop.backend.search.core.item_search.impl;

import lombok.Getter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import ru.shop.backend.search.core.item_search.ItemQueryBuilder;
import ru.shop.backend.search.core.item_search.filters.TermFilter;
import ru.shop.backend.search.model.criteria.ItemSearchCriteria;

@Component
public class ItemQueryBuilderImpl implements ItemQueryBuilder {

    private final NativeSearchQueryBuilder nativeSearchQueryBuilder;

    @Getter
    private PageRequest pageRequest;

    public ItemQueryBuilderImpl() {
        nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
    }

    @Override
    public void createQuery(ItemSearchCriteria criteria) {
        this.setPageOffset(criteria);
        this.setFilters(criteria);
        this.setAggregations(criteria);
        this.setSort(criteria);
        this.setFields(criteria);
    }

    @Override
    public NativeSearchQuery getSearchQuery() {
        return this.nativeSearchQueryBuilder.build();
    }

    protected void setFilters(ItemSearchCriteria criteria) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        if (!criteria.getTerm().isEmpty()) {
            boolQueryBuilder.must(TermFilter.createFilter(criteria));
        }

        this.nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
    }

    protected void setPageOffset(ItemSearchCriteria criteria) {
        this.pageRequest = PageRequest.of(criteria.getPage(), criteria.getSize());
        this.nativeSearchQueryBuilder.withPageable(this.pageRequest);
    }

    protected void setFields(ItemSearchCriteria criteria) {
        //TODO: add fields what is needed to search by from ElasticSearch
    }

    protected void setAggregations(ItemSearchCriteria criteria) {
        //TODO: add aggregations what is needed to search by from ElasticSearch
    }

    protected void setSort(ItemSearchCriteria criteria) {
        //TODO: add sorting what is needed to search by from ElasticSearch
    }


}
