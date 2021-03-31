package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * @author qwli7
 * 2021/2/22 13:05
 * 功能：ArticleTag
 **/
public class ArticleTag extends BaseEntity implements Serializable {

    /**
     * 文章 id
     */
    private Integer articleId;

    /**
     * 标签 id
     */
    private Integer tagId;


    public ArticleTag(){
        super();
    }

    public ArticleTag(Integer articleId, Integer tagId) {
        super();
        this.articleId = articleId;
        this.tagId = tagId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getTagId() {
        return tagId;
    }
}
