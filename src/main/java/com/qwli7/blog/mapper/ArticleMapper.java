package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.vo.ArticleQueryParam;
import com.qwli7.blog.entity.vo.HandledArticleQueryParam;
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

    Optional<Article> selectByAlias(String alias);

    Optional<Article> selectById(int id);

    int count(ArticleQueryParam queryParam);

    List<Article> selectPage(HandledArticleQueryParam queryParam);

    void addHits(int id, int hits);

    void updateHits(int id, int hits);

    void addComments(int id, int comments);

    void updateComments(int id, int comments);

    void update(Article article);

    void deleteById(int id);
}
