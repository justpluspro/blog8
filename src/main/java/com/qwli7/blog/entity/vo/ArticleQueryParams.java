package com.qwli7.blog.entity.vo;

import java.io.Serializable;

/**
 * @author qwli7 
 * @date 2023/2/16 18:09
 * 功能：blog8
 **/
public class ArticleQueryParams implements Serializable {

    private Integer page;

    private Integer size;

    private String query;

    private Integer categoryId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
