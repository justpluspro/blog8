package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.ArticleNav;
import com.qwli7.blog.service.ArticleService;

import java.util.Map;

/**
 * 文章导航 data provider
 * @author liqiwen
 * @since 3.5
 */
public class ArticleNavDataProvider extends AbstractDataProvider<ArticleNav> {

    private final ArticleService articleService;


    public ArticleNavDataProvider(ArticleService articleService) {
        super("articleNav");
        this.articleService = articleService;
    }

    @Override
    public ArticleNav queryData(Map<String, String> attributeMap) {
        int id = 0;
        try {
            id = Integer.parseInt(attributeMap.get("id"));
        } catch (NumberFormatException e){
            // ignored exception
        }
        if(id < 0) {
            return new ArticleNav();
        }
        return articleService.findArticleNav(id).orElse(new ArticleNav());
    }
}
