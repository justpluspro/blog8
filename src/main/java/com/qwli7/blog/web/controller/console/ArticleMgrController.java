package com.qwli7.blog.web.controller.console;

import com.qwli7.blog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("console")
@Controller
public class ArticleMgrController {


    private final ArticleService articleService;

    public ArticleMgrController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("articles")
    public String index() {
        return "console/article/index";
    }

    @GetMapping("article/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {

        return "console/article/edit";
    }

}
