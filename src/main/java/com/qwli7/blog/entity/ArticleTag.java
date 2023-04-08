package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * @author qwli7
 * @date 2023/4/6 15:29
 * 功能：blog8
 **/
public class ArticleTag implements Serializable {

    private Tag tag;

    private Article article;

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
