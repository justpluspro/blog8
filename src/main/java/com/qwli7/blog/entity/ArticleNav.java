package com.qwli7.blog.entity;

import java.io.Serializable;

/**
 * 文章导航
 * @author liqiwen
 * @since 1.2
 */
public class ArticleNav implements Serializable {

    /**
     * 上一篇
     */
    private Article prevArticle;

    /**
     * 下一篇
     */
    private Article nextArticle;

    public ArticleNav(Article prevArticle, Article nextArticle) {
        this.nextArticle = nextArticle;
        this.prevArticle = prevArticle;
    }

    public Article getNextArticle() {
        return nextArticle;
    }

    public void setPrevArticle(Article prevArticle) {
        this.prevArticle = prevArticle;
    }

    public Article getPrevArticle() {
        return prevArticle;
    }

    public void setNextArticle(Article nextArticle) {
        this.nextArticle = nextArticle;
    }
}


