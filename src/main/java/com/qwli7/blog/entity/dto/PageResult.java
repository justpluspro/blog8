package com.qwli7.blog.entity.dto;

import com.qwli7.blog.entity.vo.ArticleQueryParams;

import java.io.Serializable;
import java.util.List;

/**
 * @author qwli7 
 * @date 2023/2/16 18:07
 * 功能：blog8
 **/
public class PageResult<T> implements Serializable {

    private ArticleQueryParams articleQueryParams;

    private Integer currentPage;

    private Integer pageSize;

    private Long totalRow;

    private List<T> data;

    public PageResult(ArticleQueryParams articleQueryParams, long count, List<T> data) {
        this.data = data;
        this.articleQueryParams = articleQueryParams;
        this.totalRow = count;
    }

    public ArticleQueryParams getArticleQueryParams() {
        return articleQueryParams;
    }

    public void setArticleQueryParams(ArticleQueryParams articleQueryParams) {
        this.articleQueryParams = articleQueryParams;
    }

    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
