package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;

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
}
