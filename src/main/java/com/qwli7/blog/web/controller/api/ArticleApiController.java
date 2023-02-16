package com.qwli7.blog.web.controller.api;

import com.qwli7.blog.entity.dto.ArticleDto;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.vo.ArticleBean;
import com.qwli7.blog.entity.vo.ArticleQueryParams;
import com.qwli7.blog.service.ArticleService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> createArticle(@RequestBody ArticleBean articleBean) {
        articleService.saveArticle(articleBean);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("articles")
    public ResponseEntity<PageResult<ArticleDto>> queryArticles(ArticleQueryParams articleQueryParams) {
        PageResult<ArticleDto> articleDtoPageResult = articleService.queryArticle(articleQueryParams);
        return ResponseEntity.ok(articleDtoPageResult);
    }
}
