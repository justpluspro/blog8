package com.qwli7.blog.event;

import com.qwli7.blog.entity.Article;
import org.springframework.context.ApplicationEvent;

public class ArticlePostEvent extends ApplicationEvent {

    private final Article article;

    public Article getArticle() {
        return article;
    }

    public ArticlePostEvent(Object source, Article article) {
        super(source);
        this.article = article;
    }
}
