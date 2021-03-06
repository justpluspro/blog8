package com.qwli7.blog.service;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.ArticleSaved;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.ArticleQueryParam;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
public interface ArticleService {
    ArticleSaved save(Article article);

    PageDto<Article> selectPage(ArticleQueryParam queryParam);

    void deleteById(int id);
}
