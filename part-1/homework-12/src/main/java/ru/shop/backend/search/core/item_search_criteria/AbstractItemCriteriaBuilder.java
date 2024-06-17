package ru.shop.backend.search.core.item_search_criteria;

import ru.shop.backend.search.model.criteria.ItemSearchCriteria;

public abstract class AbstractItemCriteriaBuilder {

    abstract ItemSearchCriteria getCriteria();

    abstract void createCriteria();
}
