package org.minjay.gamers.dynamic.data.elasticsearch.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.minjay.gamers.dynamic.data.elasticsearch.domain.Dynamic;
import org.minjay.gamers.dynamic.service.model.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
                .withHighlightFields(new HighlightBuilder.Field("content"), new HighlightBuilder.Field("username"))
                .withQuery(builder)
                .withFilter(geoDistanceQueryBuilder)
                .withSearchType(SearchType.QUERY_THEN_FETCH)
                .withPageable(pageable)
                .build();
        return elasticsearchTemplate.queryForPage(searchQuery, Dynamic.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                if (response.getHits().getHits().length < 1) {
                    return new AggregatedPageImpl<>(Collections.EMPTY_LIST);
                }
                List<Dynamic> dynamics = new ArrayList<>();
                ObjectMapper mapper = new ObjectMapper();
                for (SearchHit searchHit : response.getHits()) {
                    Dynamic dynamic = null;
                    try {
                        dynamic = mapper.readValue(searchHit.getSourceAsString(), Dynamic.class);
                    } catch (Exception ex) {

                    }
                    dynamic.setId(searchHit.getId());
                    HighlightField highlightContent = searchHit.getHighlightFields().get("content");
                    if (highlightContent != null) {
                        dynamic.setContent(highlightContent.fragments()[0].toString());
                    }
                    HighlightField usernameContent = searchHit.getHighlightFields().get("username");
                    if (usernameContent != null) {
                        dynamic.setContent(usernameContent.fragments()[0].toString());
                    }
                    dynamics.add(dynamic);
                }
                return new AggregatedPageImpl<>((List<T>) dynamics);
            }
        }).getContent();
    }

    private String getWildcardString(String value) {
        return "*" + value + "*";
    }
}
