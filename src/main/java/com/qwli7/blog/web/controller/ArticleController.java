package com.qwli7.blog.web.controller;

import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.ArticleSaved;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.ArticleQueryParam;
import com.qwli7.blog.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author qwli7
 * @date 2021/2/22 13:11
 * 功能：blog8
 **/
@RestController
@RequestMapping("api")
public class ArticleController {


    private final ArticleService articleService;
    private final BlogProperties blogProperties;

    public ArticleController(ArticleService articleService, BlogProperties blogProperties) {
        this.articleService = articleService;
        this.blogProperties = blogProperties;
    }


    @PostMapping("article")
    public ResponseEntity<?> save(@RequestBody @Valid Article article) {
            ArticleSaved articleSaved = articleService.save(article);
            return ResponseEntity.ok(articleSaved);
    }

    @GetMapping("articles")
    public PageDto<Article> selectPage(ArticleQueryParam queryParam) {
        if(queryParam.hasNoSize()) {
            queryParam.setSize(blogProperties.getDefaultPageSize());
        }
        return articleService.selectPage(queryParam);
    }

    @PutMapping("article/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") int id, @RequestBody Article article) {
        article.setId(id);
        articleService.update(article);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("article/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        articleService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
