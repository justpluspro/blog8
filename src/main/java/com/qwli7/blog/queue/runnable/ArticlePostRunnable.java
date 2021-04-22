package com.qwli7.blog.queue.runnable;

import com.qwli7.blog.entity.Article;

/**
 * 文章发布线程
 * @author liqiwen
 * @since 2.1
 */
public class ArticlePostRunnable implements Runnable {

    /**
     * 待发布的文章
     */
    private final Article article;

    public Article getArticle() {
        return article;
    }

    public ArticlePostRunnable(Article article) {
        this.article = article;
    }

    @Override
    public void run() {
        // 变更文章的状态

        // 插入索引

    }
}
