package com.qwli7.blog.queue.runnable;

import com.qwli7.blog.entity.Article;

public class ArticlePostRunnable implements Runnable {

    private final Article article;

    public Article getArticle() {
        return article;
    }

    public ArticlePostRunnable(Article article) {
        this.article = article;
    }

    @Override
    public void run() {

    }
}
