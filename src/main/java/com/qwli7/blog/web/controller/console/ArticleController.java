package com.qwli7.blog.web.controller.console;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qwli7 
 * @date 2023/2/16 15:01
 * 功能：blog8
 **/
@RequestMapping("console")
@Controller
public class ArticleController {


    @GetMapping("articles")
    public String articles() {

        return "console/articles";
    }

    @GetMapping("article/edit")
    public String editArticle() {
        return "console/article_edit";
    }
}
