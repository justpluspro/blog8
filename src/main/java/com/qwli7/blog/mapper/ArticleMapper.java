package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.vo.ArticleQueryParams;
import com.qwli7.blog.entity.vo.HandledArticleQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/16 15:00
 * 功能：blog8
 **/
@Mapper
public interface ArticleMapper {

    Optional<Article> findByAlias(@Param("alias") String alias);

    void insert(Article article);

    Optional<Article> findById(@Param("id") Integer id);

    void deleteById(@Param("id") Integer id);

    void updateHits(Article article);

    Integer count(HandledArticleQueryParams handledArticleQueryParams);

    List<Article> findArticle(HandledArticleQueryParams handledArticleQueryParams);
}
