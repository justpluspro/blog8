package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.service.ArticleService;

import java.util.Map;
import java.util.Optional;

/**
 * 单篇文章数据提供者
 * @author liqiwen
 * @since 2.5
 */
public class ArticleDataProvider extends AbstractDataProvider<Article> {


    private final ArticleService articleService;


    public ArticleDataProvider(ArticleService articleService) {
        super("article");
        this.articleService = articleService;
    }

    @Override
    public Article queryData(Map<String, String> attributeMap) {
        final String idOrAlias = attributeMap.get("idOrAlias");
        final Optional<Article> articleOp = articleService.getArticle(idOrAlias);
        if(!articleOp.isPresent()) {
            throw new ResourceNotFoundException("article.notExists", "文章未找到");
        }
        return articleOp.get();
    }
}
