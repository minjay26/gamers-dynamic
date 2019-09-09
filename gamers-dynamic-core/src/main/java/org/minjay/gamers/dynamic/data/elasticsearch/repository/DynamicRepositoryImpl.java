package org.minjay.gamers.dynamic.data.elasticsearch.repository;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.minjay.gamers.dynamic.service.model.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.Collection;

import static org.elasticsearch.index.query.QueryBuilders.fuzzyQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

public class DynamicRepositoryImpl implements DynamicRepositoryCustom {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Collection<Dynamic> search(SearchCriteria criteria, Pageable pageable) {
        BoolQueryBuilder builder = new BoolQueryBuilder();
        if (criteria.getUserId() != null) {
            builder.must(termQuery("userId", criteria.getUserId()));
        }

        if (StringUtils.isNotBlank(criteria.getKeyword())) {
            if (StringUtils.isNotBlank(criteria.getSearchType())) {
                if (criteria.getSearchType().equals("user")) {
                    builder.must(fuzzyQuery("username", criteria.getKeyword()));
                } else {
                    builder.must(fuzzyQuery("content", criteria.getKeyword()));
                }
            } else {
                builder.should(fuzzyQuery("content", criteria.getKeyword())).
                        should(fuzzyQuery("username", criteria.getKeyword()));
            }
        }


        GeoDistanceQueryBuilder geoDistanceQueryBuilder = null;
        if (criteria.getLat() != null) {
            geoDistanceQueryBuilder = new GeoDistanceQueryBuilder("location").point(criteria.getLat(), criteria.getLon())
                    .distance(String.valueOf(3 * 1000));
        }

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("dynamic")
                .withTypes("dynamic")
                .withQuery(builder)
                .withFilter(geoDistanceQueryBuilder)
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withPageable(pageable)
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, Dynamic.class);
    }

    private String getWildcardString(String value) {
        return "*" + value + "*";
    }
}
