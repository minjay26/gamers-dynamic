package org.minjay.gamers.dynamic.service.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class SearchCriteria {

    private Long userId;
    private String keyword;

    private String searchType;

    private Double lon;
    private Double lat;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
