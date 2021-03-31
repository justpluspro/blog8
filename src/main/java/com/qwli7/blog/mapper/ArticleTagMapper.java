package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.ArticleTag;
import com.qwli7.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author qwli7
 * 2021/2/22 13:49
 * 功能：ArticleTagMapper
 **/
@Mapper
public interface ArticleTagMapper {

    /**
     * 插入 ArticleTag
     * @param articleTag articleTag
     */
    void insert(ArticleTag articleTag);

    /**
     * 批量插入 ArticleTag
     * @param articleTags articleTags
     */
    void batchInsert(List<ArticleTag> articleTags);

    /**
     * 根据文章删除 ArticleTag
     * @param article article
     */
    void deleteByArticle(Article article);

    /**
     * 根据标签删除 ArticleTag
     * @param tag tag
     */
    void deleteByTag(Tag tag);
}
