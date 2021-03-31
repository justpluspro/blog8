package com.qwli7.blog.event;

import com.qwli7.blog.entity.Article;
import org.springframework.context.ApplicationEvent;

/**
 * 文章删除事件
 * @author liqiwen
 * @since 1.2
 */
public class ArticleDeleteEvent extends ApplicationEvent {

    /**
     * 删除的文章
     */
    private final Article article;

    public Article getArticle() {
        return article;
    }

    public ArticleDeleteEvent(Object source, Article article) {
        super(source);
        this.article = article;
    }
}
