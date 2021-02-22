package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2021/2/22 13:05
 * 功能：blog8
 **/
public class ArticleTag extends BaseEntity implements Serializable {

    private Integer articleId;

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
