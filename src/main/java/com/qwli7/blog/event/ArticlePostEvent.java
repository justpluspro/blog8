package com.qwli7.blog.event;

import com.qwli7.blog.entity.Article;
import org.springframework.context.ApplicationEvent;

/**
 * 文章发布事件
 * @author liqiwen
 * @since 1.2
 */
public class ArticlePostEvent extends ApplicationEvent {

    /**
     * 当前已发布的文章
     */
    private final Article article;

    public Article getArticle() {
        return article;
    }

    public ArticlePostEvent(Object source, Article article) {
        super(source);
        this.article = article;
    }
}
