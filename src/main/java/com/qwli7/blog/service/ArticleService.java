package com.qwli7.blog.service;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.ArticleNav;
import com.qwli7.blog.entity.ArticleSaved;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.ArticleQueryParam;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * 2021/2/22 13:09
 * 功能：ArticleService
 **/
public interface ArticleService {

    /**
     * 保存文章
     * @param article article
     * @return ArticleSaved
     */
    ArticleSaved save(Article article);

    /**
     * 更新文章
     * @param article article
     */
    void update(Article article);

    /**
     * 查找文章
     * @param queryParam queryParam
     * @return PageDto
     */
    PageDto<Article> selectPage(ArticleQueryParam queryParam);

    /**
     * 删除文章
     * @param id id
     */
    void deleteById(int id);

    /**
     * 获取文章为编辑
     * @param id id
     * @return Article
     */
    Optional<Article> getArticleForEdit(int id);

    /**
     * 获取文章
     * @param idOrAlias id 或者别名
     * @return Article
     */
    Optional<Article> getArticle(String idOrAlias);

    /**
     * 获取文章导航
     * @param id id
     * @return ArticleNav
     */
    Optional<ArticleNav> selectArticleNav(int id);

    /**
     * 更新点击量
     * @param id id
     */
    void hits(int id);


    void deleteByIds(List<Integer> ids);
}
