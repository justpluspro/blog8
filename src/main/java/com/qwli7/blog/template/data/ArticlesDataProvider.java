package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.ArticleQueryParam;
import com.qwli7.blog.service.ArticleService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 文章数据提供者
 * @author liqiwen
 * @since 2.5
 */
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
        ArticleQueryParam queryParam = new ArticleQueryParam();
        int page;
        try {
            page = Integer.parseInt(attributeMap.get("page"));
        } catch (NumberFormatException e){
            page = 1;
        }
        int size;
        try {
            size = Integer.parseInt(attributeMap.get("size"));
        } catch (NumberFormatException e) {
            size = 10;
        }
        final String query = attributeMap.getOrDefault("query", "");
        final String tag = attributeMap.getOrDefault("tag", "");

        queryParam.setPage(Math.max(page, 1));
        queryParam.setSize(size < 10 || size > 20 ? 10: size);
        queryParam.setQuery(query);

        return articleService.selectPage(queryParam);
    }
}
