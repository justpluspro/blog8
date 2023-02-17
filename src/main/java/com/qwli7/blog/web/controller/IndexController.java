package com.qwli7.blog.web.controller;

import com.qwli7.blog.entity.dto.ArticleDto;
import com.qwli7.blog.entity.vo.ArticleQueryParams;
import com.qwli7.blog.exception.Message;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author qwli7 
 * @date 2023/2/16 15:54
 * 功能：blog8
 **/
@Controller
public class IndexController {


    private final ArticleService articleService;

    public IndexController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @GetMapping
    public String index(ArticleQueryParams articleQueryParams, Model model) {
//        PageResult<ArticleDto> articlePageResult = articleService.queryArticle(articleQueryParams);
//        model.addAttribute(articlePageResult);
        return "index";
    }


    @GetMapping("article/{idOrAlias}")
    private String showArticleInfo(@PathVariable("idOrAlias") String idOrAlias, Model model) {
        if(StringUtils.isEmpty(idOrAlias)) {
            throw new ResourceNotFoundException(Message.ARTICLE_NOT_FOUND);
        }
        ArticleDto articleDto = articleService.getArticleForView(idOrAlias);
        model.addAttribute(articleDto);
        return "article";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("upload")
    public String fileUpload() {
        return "file_upload";
    }
}
