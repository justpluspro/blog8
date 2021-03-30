package com.qwli7.blog.entity.vo;

import java.io.Serializable;

/**
 * 文章查询参数
 * @author liqiwen
 * @since 1.2
 */
public class ArticleQueryParam extends AbstractQueryParam implements Serializable {

    /**
     * 关键字
     */
    private String query;

    /**
     * 分类 id
     */
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
