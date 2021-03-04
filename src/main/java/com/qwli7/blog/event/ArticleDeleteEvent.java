package com.qwli7.blog.event;

import com.qwli7.blog.entity.Article;
import org.springframework.context.ApplicationEvent;

public class ArticleDeleteEvent extends ApplicationEvent {

    private final Article article;

    public Article getArticle() {
        return article;
    }

    public ArticleDeleteEvent(Object source, Article article) {
        super(source);
        this.article = article;
    }
}
