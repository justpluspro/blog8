package com.qwli7.blog.event;

import com.qwli7.blog.entity.Article;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author liqiwen
 * @date 2021-04-11
 *
 * Article Batch Delete
 */
public class ArticleBatchDeleteEvent extends ApplicationEvent {

    private final List<Article> articles;

    public ArticleBatchDeleteEvent(Object source, List<Article> articles) {
        super(source);
        this.articles = articles;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
