package com.qwli7.blog.web.controller;

import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.ArticleSaved;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.ArticleQueryParam;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:11
 * 功能：ArticleController
 **/
@Authenticated
@RestController
@RequestMapping("api")
//@Validated
public class ArticleController {


    private final ArticleService articleService;
    private final BlogProperties blogProperties;

    public ArticleController(ArticleService articleService, BlogProperties blogProperties) {
        this.articleService = articleService;
        this.blogProperties = blogProperties;
    }

    /**
     * 保存文章
     * @param article article
     * @return ResponseEntity
     */
    @PostMapping("article")
    public ResponseEntity<?> save(@RequestBody Article article) {
        ArticleSaved articleSaved = articleService.save(article);
        return ResponseEntity.ok(articleSaved);
    }

    /**
     * 获取文章列表
     * @param queryParam queryParam
     * @return PageDto
     */
    @GetMapping("articles")
    public PageDto<Article> findPage(ArticleQueryParam queryParam) {
        if(queryParam.hasNoSize()) {
            queryParam.setSize(blogProperties.getDefaultPageSize());
        }
        return articleService.findPage(queryParam);
    }

    /**
     * 获取文章，编辑
     * @param id id
     * @return Article
     */
    @GetMapping("article/{id}")
    public Article getArticleForEdit(@PathVariable("id") @Min(value = 1, message = "invalid id") int id) {
        final Optional<Article> articleOp = articleService.findArticleForEdit(id);
        if(!articleOp.isPresent()) {
            throw new ResourceNotFoundException("article.notFound", "内容没找到");
        }
        return articleOp.get();
    }

    /**
     * 更新文章
     * @param id id
     * @param article article
     * @return ResponseEntity
     */
    @PutMapping("article/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody @Valid Article article) {
        article.setId(id);
        articleService.update(article);
        return ResponseEntity.noContent().build();
    }

    /**
     * 删除文章
     * @param id id
     * @return ResponseEntity
     */
    @DeleteMapping("article/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        articleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 批量删除文章
     * @param ids ids
     * @return ResponseEntity
     */
    @DeleteMapping("article/delete/batch")
    public ResponseEntity<?> deleteInBatch(@RequestBody List<Integer> ids) {
        articleService.deleteByIds(ids);
        return ResponseEntity.noContent().build();
    }
}
