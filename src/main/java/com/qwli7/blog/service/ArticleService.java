package com.qwli7.blog.service;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.dto.ArticleDto;
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


    void saveArticle(ArticleBean articleBean);


    PageResult<ArticleDto> queryArticle(ArticleQueryParams articleQueryParams);


    void updateArticle(ArticleBean articleBean);

    ArticleDto getArticleForView(String idOrAlias);

    Optional<Article> getArticleForEdit(Integer id);

    void hitArticle(Integer id);

    void deleteArticle(Integer id);
}
