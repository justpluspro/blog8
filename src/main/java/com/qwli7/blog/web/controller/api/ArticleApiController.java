package com.qwli7.blog.web.controller.api;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.enums.ArticleStatus;
import com.qwli7.blog.entity.vo.ArticleQueryParams;
import com.qwli7.blog.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author qwli7
 * @date 2023/2/16 16:56
 * 功能：blog8
 **/
@RestController
@RequestMapping("api")
public class ArticleApiController {

    private final ArticleService articleService;

    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("article")
    public ResponseEntity<Void> addArticle(@RequestBody @Validated Article article) {
        articleService.addArticle(article);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("article")
    public ResponseEntity<Void> updateArticle(@RequestBody Article article) {
        articleService.updateArticle(article);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("article/{idOrAlias}")
    public ResponseEntity<Article> getArticle(@PathVariable("idOrAlias") String idOrAlias) {
        Article articleForView = articleService.getArticleForView(idOrAlias);
        return ResponseEntity.ok(articleForView);
    }


    @DeleteMapping("article/{id}/delete")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("articles")
//    public ResponseEntity<PageResult<ArticleDto>> queryArticles(ArticleQueryParams articleQueryParams) {
//        PageResult<ArticleDto> articleDtoPageResult = articleService.queryArticle(articleQueryParams);
//        return ResponseEntity.ok(articleDtoPageResult);
//    }

    @GetMapping("articles")
    public PageResult<Article> findArticles(@Validated ArticleQueryParams articleQueryParams) {
        if (articleQueryParams.getStatus() == null) {
            articleQueryParams.setStatus(ArticleStatus.POSTED);
        }
        return articleService.findArticle(articleQueryParams);
    }

    @PutMapping("article/{id}/hit")
    public ResponseEntity<Void> addHits(@PathVariable("id") Integer id) {
        articleService.hitArticle(id);
        return ResponseEntity.noContent().build();
    }
}
