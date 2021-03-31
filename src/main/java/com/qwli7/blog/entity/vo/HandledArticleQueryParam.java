package com.qwli7.blog.entity.vo;

import com.qwli7.blog.entity.ArticleStatus;
import com.qwli7.blog.entity.Category;

import java.util.List;

/**
 * 文章查询参数
 * @author liqiwen
 * @since 1.0
 */
public class HandledArticleQueryParam extends AbstractQueryParam {

    /**
     * 关键字
     */
    private String query;

    /**
     * 分类
     */
    private Category category;

    /**
     * 文章 ids
     */
    private List<Integer> aids;

    /**
     * 文章状态集合
     */
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
        super();
        this.setPage(queryParam.getPage());
        this.setSize(queryParam.getSize());
        this.setOffset(queryParam.getOffset());
        this.setStart(queryParam.getStart());
    }
}
