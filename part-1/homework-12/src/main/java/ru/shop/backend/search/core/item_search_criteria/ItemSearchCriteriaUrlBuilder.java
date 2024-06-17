package ru.shop.backend.search.core.item_search_criteria;

import ru.shop.backend.search.model.criteria.ItemSearchCriteria;
import ru.shop.backend.search.dto.request.SearchRequestModel;

public class ItemSearchCriteriaUrlBuilder extends AbstractItemCriteriaBuilder {

    private SearchRequestModel searchRequestModel;
    private ItemSearchCriteria itemSearchCriteria;

    public ItemSearchCriteriaUrlBuilder(SearchRequestModel searchRequestModel) {
        this.searchRequestModel = searchRequestModel;
        this.itemSearchCriteria = new ItemSearchCriteria();
    }

    @Override
    public ItemSearchCriteria getCriteria() {
        return this.itemSearchCriteria;
    }

    @Override
    public void createCriteria() {
        if (searchRequestModel.getPage() >= 1) {
            itemSearchCriteria.setPage(searchRequestModel.getPage() - 1);
        }

        if (searchRequestModel.getSize() <= ItemSearchCriteria.MAX_SIZE &&
                searchRequestModel.getSize() >= ItemSearchCriteria.MIN_SIZE) {
            itemSearchCriteria.setSize(searchRequestModel.getSize());
        }

//        set search info about item
        if (searchRequestModel.getTerm() != null) {
            itemSearchCriteria.setTerm(searchRequestModel.getTerm());
        }

        if (searchRequestModel.getRegionId() != null) {
            itemSearchCriteria.setRegionId(searchRequestModel.getRegionId());
        }



    }




}
