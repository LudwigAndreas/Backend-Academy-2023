package ru.shop.backend.search.core.item_search.filters;

import org.elasticsearch.index.query.*;
import ru.shop.backend.search.model.criteria.ItemSearchCriteria;

import java.util.HashMap;

public class TermFilter {

    public static AbstractQueryBuilder<?> createFilter(ItemSearchCriteria criteria) {
        if (criteria.getTerm().isEmpty()) {
            return queryMatchNone();
        } else {
            return buildBoolQuery(criteria);
        }
    }

    private static AbstractQueryBuilder<BoolQueryBuilder> buildBoolQuery(ItemSearchCriteria criteria) {
        int fuzziness = 0;
        if (criteria.getTerm().length() > 3) {
            fuzziness = 2;
        }

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(
                "name",
                criteria.getTerm()
        ).operator(Operator.AND).boost(10);

        MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(
                criteria.getTerm()
        )
                .fields(new HashMap<>() {{
                            put("name", 10f);
                            put("brandName", 4f);
                            put("type", 4f);
                            put("catalogue", 3f);
                            put("description", 2f);
                        }}
                )
                .operator(Operator.AND)
                .boost(2)
                .fuzziness(fuzziness);

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.should(matchQueryBuilder);
        boolQueryBuilder.should(multiMatchQueryBuilder);
        boolQueryBuilder.minimumShouldMatch(1);

        return boolQueryBuilder;
    }

    private static AbstractQueryBuilder<MatchNoneQueryBuilder> queryMatchNone() {
        MatchNoneQueryBuilder matchNone = new MatchNoneQueryBuilder();
        return matchNone;
    }
}