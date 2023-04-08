package com.qwli7.blog.entity.vo;

import com.qwli7.blog.entity.enums.ArticleStatus;

import java.io.Serializable;

/**
 * @author qwli7 
 * @date 2023/2/16 18:09
 * 功能：blog8
 **/
public class ArticleQueryParams extends PageQueryParams implements Serializable {

    private String query;

    private Integer categoryId;

    private ArticleStatus status;

    private Boolean queryPrivate;

    public Boolean getQueryPrivate() {
        return queryPrivate;
    }

    public void setQueryPrivate(Boolean queryPrivate) {
        this.queryPrivate = queryPrivate;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

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
