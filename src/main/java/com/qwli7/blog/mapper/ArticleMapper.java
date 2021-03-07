package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.vo.ArticleQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:49
 * 功能：blog8
 **/
@Mapper
public interface ArticleMapper {
    void insert(Article article);

    Optional<Article> findByAlias(String alias);

    Optional<Article> findById(int id);

    int count(ArticleQueryParam queryParam);

    List<Article> selectPage(ArticleQueryParam queryParam);

    void addHits(int id, int hits);

    void update(Article article);

    void deleteById(int id);
}
