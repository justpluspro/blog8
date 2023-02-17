package com.qwli7.blog.web.controller.console;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.exception.Message;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @author qwli7 
 * @date 2023/2/16 15:01
 * 功能：blog8
 **/
@RequestMapping("console")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("articles")
    public String articles() {
        return "console/articles";
    }

    @GetMapping("article/create")
    public String createArticle() {
        return "console/article_edit";
    }

    @GetMapping("article/{id}/edit")
    public String editArticle(@PathVariable("id") String idStr, Model model) {
        Integer id = null;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            throw new ResourceNotFoundException(Message.ARTICLE_NOT_FOUND);
        }
        if(id <= 0) {
            throw new ResourceNotFoundException(Message.ARTICLE_NOT_FOUND);
        }
        Optional<Article> articleOptional = articleService.getArticleForEdit(id);
        if(!articleOptional.isPresent()) {
            throw new ResourceNotFoundException(Message.ARTICLE_NOT_FOUND);
        }
        model.addAttribute(articleOptional.get());
        return "console/article_edit";
    }
}
