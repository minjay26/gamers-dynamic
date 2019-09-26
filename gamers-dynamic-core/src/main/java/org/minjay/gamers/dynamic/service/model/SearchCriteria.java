package org.minjay.gamers.dynamic.service.model;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria {

    private List<Long> userIds = new ArrayList<>();
    private String keyword;

    private String searchType;

    private boolean includeFocus = true;

    private Double lon;
    private Double lat;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
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

    public boolean isIncludeFocus() {
        return includeFocus;
    }

    public void setIncludeFocus(boolean includeFocus) {
        this.includeFocus = includeFocus;
    }
}
