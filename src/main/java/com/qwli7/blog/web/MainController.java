package com.qwli7.blog.web;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.MomentArchive;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.MomentQueryParam;
import com.qwli7.blog.service.ArticleService;
import com.qwli7.blog.service.MomentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class MainController {

    private final MomentService momentService;

    private final ArticleService articleService;

    public MainController(MomentService momentService, ArticleService articleService) {
        this.momentService = momentService;
        this.articleService = articleService;
    }


    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("article/{idOrAlias}")
    public String articleDetail(@PathVariable("idOrAlias") String idOrAlias, Model model) {
        Optional<Article> articleOptional = articleService.getArticle(idOrAlias);
        if(articleOptional.isPresent()){
        }
        return "";
    }


    @GetMapping("moments")
    public String moments(MomentQueryParam queryParam) {

        return "moments";
    }

    @GetMapping("moments/archive")
    @ResponseBody
    public PageDto<MomentArchive> select(MomentQueryParam queryParam) {
        return momentService.selectArchivePage(queryParam);
    }
}
