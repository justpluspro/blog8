package com.qwli7.blog.service;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.dto.ArticleDetail;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.vo.ArticleBean;
import com.qwli7.blog.entity.vo.ArticleQueryParams;

import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/16 15:00
 * 功能：blog8
 **/
public interface ArticleService {

    void addArticle(Article article);

    PageResult<ArticleDetail> queryArticle(ArticleQueryParams articleQueryParams);

    PageResult<Article> findArticle(ArticleQueryParams articleQueryParams);

    void updateArticle(Article article);

    Article getArticleForView(String idOrAlias);

    Optional<Article> getArticleForEdit(Integer id);

    void hitArticle(Integer id);

    void deleteArticle(Integer id);

}
