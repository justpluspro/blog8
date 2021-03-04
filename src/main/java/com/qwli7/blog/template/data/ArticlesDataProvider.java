package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.service.ArticleService;
import com.qwli7.blog.service.impl.ArticleServiceImpl;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ArticlesDataProvider extends AbstractDataProvider<PageDto<Article>>{

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
