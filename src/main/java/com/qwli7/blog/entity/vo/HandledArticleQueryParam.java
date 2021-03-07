package com.qwli7.blog.entity.vo;

import com.qwli7.blog.entity.Category;

public class HandledArticleQueryParam extends AbstractQueryParam {

    private String query;

    private Category category;


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

    public HandledArticleQueryParam(ArticleQueryParam queryParam) {

    }
}
