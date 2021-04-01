package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.vo.ArticleQueryParam;
import com.qwli7.blog.entity.vo.HandledArticleQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * 2021/2/22 13:49
 * 功能：ArticleMapper
 **/
@Mapper
public interface ArticleMapper {

    /**
     * 保存文章
     * @param article article
     */
    void insert(Article article);

    /**
     * 根据别名获取文章
     * @param alias alias
     * @return Article
     */
    Optional<Article> selectByAlias(String alias);

    /**
     * 根据 id 获取文章
     * @param id id
     * @return Article
     */
    Optional<Article> selectById(int id);

    /**
     * 统计文章数量
     * @param queryParam queryParam
     * @return int
     */
    int count(ArticleQueryParam queryParam);

    /**
     * 分页查询文章
     * @param queryParam queryParam
     * @return List
     */
    List<Article> selectPage(HandledArticleQueryParam queryParam);

    /**
     * 添加文章点击量
     * @param id id
     * @param hits hits
     */
    void addHits(int id, int hits);

    /**
     * 更新文章点击量
     * @param id id
     * @param hits hits
     */
    void updateHits(int id, int hits);

    /**
     * 添加评论数量
     * @param id id
     * @param comments comments
     */
    void addComments(int id, int comments);

    /**
     * 更新评论数量
     * @param id id
     * @param comments comments
     */
    void updateComments(int id, int comments);

    /**
     * 更新文章
     * @param article article
     */
    void update(Article article);

    /**
     * 删除文章
     * @param id id
     */
    void deleteById(int id);
}
