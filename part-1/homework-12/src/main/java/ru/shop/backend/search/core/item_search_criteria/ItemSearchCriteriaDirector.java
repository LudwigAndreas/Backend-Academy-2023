package ru.shop.backend.search.core.item_search_criteria;

import ru.shop.backend.search.model.criteria.ItemSearchCriteria;

public class ItemSearchCriteriaDirector extends AbstractItemCriteriaDirector {

    public ItemSearchCriteriaDirector(AbstractItemCriteriaBuilder builder) {
        super(builder);
    }

    @Override
    public void buildCriteria() {
        builder.createCriteria();
    }

    @Override
    public ItemSearchCriteria getCriteria() {
        return builder.getCriteria();
    }
}
