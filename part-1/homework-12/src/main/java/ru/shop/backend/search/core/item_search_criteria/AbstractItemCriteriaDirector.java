package ru.shop.backend.search.core.item_search_criteria;

import ru.shop.backend.search.model.criteria.SearchCriteria;

public abstract class AbstractItemCriteriaDirector {

    protected AbstractItemCriteriaBuilder builder;

    public AbstractItemCriteriaDirector(AbstractItemCriteriaBuilder builder) {
        this.builder = builder;
    }

    public abstract void buildCriteria();

    public abstract SearchCriteria getCriteria();

}
