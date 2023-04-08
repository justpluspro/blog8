package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.ArticleTag;
import com.qwli7.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qwli7
 * @date 2023/2/20 15:30
 * 功能：blog8
 **/
@Mapper
public interface ArticleTagMapper {


    void insert(ArticleTag tag);

    void deleteByArticle(Article article);

    void deleteByTag(Tag tag);

    void batchInsert(List<ArticleTag> articleTagList);
}
