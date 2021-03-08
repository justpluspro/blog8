package com.qwli7.blog.entity.vo;

import com.qwli7.blog.entity.ArticleStatus;
import com.qwli7.blog.entity.Category;

import java.util.List;

public class HandledArticleQueryParam extends AbstractQueryParam {

    private String query;

    private Category category;

    private List<Integer> aids;

    private List<ArticleStatus> statuses;

    public List<ArticleStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<ArticleStatus> statuses) {
        this.statuses = statuses;
    }

    public List<Integer> getAids() {
        return aids;
    }

    public void setAids(List<Integer> aids) {
        this.aids = aids;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public HandledArticleQueryParam() {
        super();
    }

    public HandledArticleQueryParam(ArticleQueryParam queryParam) {
        this.setPage(queryParam.getPage());
        this.setSize(queryParam.getSize());
        this.setOffset(queryParam.getOffset());
        this.setStart(queryParam.getStart());
    }
}
