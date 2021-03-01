package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.mapper.ArticleMapper;
import com.qwli7.blog.service.ArticleService;
import com.qwli7.blog.service.CommentModuleHandler;
import com.qwli7.blog.service.Markdown2Html;
import org.springframework.stereotype.Service;


/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
@Service
public class ArticleServiceImpl implements ArticleService, CommentModuleHandler {


    private final ArticleMapper articleMapper;
    private final Markdown2Html markdown2Html;

    public ArticleServiceImpl(Markdown2Html markdown2Html,  ArticleMapper articleMapper) {
        this.markdown2Html = markdown2Html;
        this.articleMapper = articleMapper;
    }



    @Override
    public String getModuleName() {
        return Article.class.getSimpleName().toLowerCase();
    }
}
