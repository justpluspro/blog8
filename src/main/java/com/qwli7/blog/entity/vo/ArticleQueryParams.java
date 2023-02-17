package com.qwli7.blog.entity.vo;

import java.io.Serializable;

/**
 * @author qwli7 
 * @date 2023/2/16 18:09
 * 功能：blog8
 **/
public class ArticleQueryParams extends PageQueryParams implements Serializable {

    private String query;

    private Integer categoryId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
