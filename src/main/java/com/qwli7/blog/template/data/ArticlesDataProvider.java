package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.service.ArticleService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 文章数据提供者
 * @author liqiwen
 * @since 2.5
 */
@Component
public class ArticlesDataProvider extends AbstractDataProvider<PageDto<Article>>{

    /**
     * ArticleService
     */
    private final ArticleService articleService;

    public ArticlesDataProvider(ArticleService articleService) {
        super("articles");
        this.articleService = articleService;
    }

    @Override
    public PageDto<Article> queryData(Map<String, String> attributeMap) {
        return null;
    }
}
