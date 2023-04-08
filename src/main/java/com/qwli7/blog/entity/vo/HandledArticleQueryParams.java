package com.qwli7.blog.entity.vo;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.entity.enums.ArticleStatus;

import java.io.Serializable;

/**
 * @author qwli7 (qwli7@iflytek.com)
 * @date 2023/4/8 11:45
 * 功能：blog8
 **/
public class HandledArticleQueryParams extends PageQueryParams implements Serializable {

    private Category category;

    private ArticleStatus status;

    private Boolean queryPrivate;

    public HandledArticleQueryParams(ArticleQueryParams articleQueryParams) {
        this.page = articleQueryParams.getPage();
        this.size = articleQueryParams.getSize();
        this.status = articleQueryParams.getStatus();
        this.queryPrivate = articleQueryParams.getQueryPrivate();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public Boolean getQueryPrivate() {
        return queryPrivate;
    }

    public void setQueryPrivate(Boolean queryPrivate) {
        this.queryPrivate = queryPrivate;
    }
}
