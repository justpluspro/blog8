package com.qwli7.blog.web.controller;

import com.qwli7.blog.service.ArticleService;
import com.qwli7.blog.service.MomentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 点击控制器
 * @author liqiwen
 * @since 1.2
 */
@RestController
@RequestMapping("api")
public class HitController {


    private final MomentService momentService;
    private final ArticleService articleService;

    public HitController(MomentService momentService, ArticleService articleService) {
        this.momentService = momentService;
        this.articleService = articleService;
    }


    /**
     * 更新点击量|文章
     * @param id id
     * @return ResponseEntity
     */
    @PatchMapping("article/{id}/hits")
    public ResponseEntity<Void> articleHits(@PathVariable("id") int id) {
        articleService.hits(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 更新动态点击量
     * @param id id
     * @return ResponseEntity
     */
    @PatchMapping("moment/{id}/hits")
    public ResponseEntity<Void> momentHits(@PathVariable("id") int id) {
        momentService.hits(id);
        return ResponseEntity.noContent().build();
    }


}
