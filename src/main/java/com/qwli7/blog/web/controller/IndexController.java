package com.qwli7.blog.web.controller;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.MomentArchiveDetail;
import com.qwli7.blog.entity.dto.MomentNav;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.enums.ArticleStatus;
import com.qwli7.blog.entity.enums.MomentStatus;
import com.qwli7.blog.entity.vo.ArticleQueryParams;
import com.qwli7.blog.entity.vo.MomentQueryParams;
import com.qwli7.blog.exception.Message;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.service.ArticleService;
import com.qwli7.blog.service.MomentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/16 15:54
 * 功能：blog8
 **/
@Controller
public class IndexController {


    private final ArticleService articleService;

    private final MomentService momentService;

    public IndexController(ArticleService articleService, MomentService momentService) {
        this.articleService = articleService;
        this.momentService = momentService;
    }


    @GetMapping
    public String index(ArticleQueryParams articleQueryParams, Model model) {
        ArticleStatus status = articleQueryParams.getStatus();
        if(status == null) {
            articleQueryParams.setStatus(ArticleStatus.POSTED);
        }
        Boolean queryPrivate = articleQueryParams.getQueryPrivate();
        if(queryPrivate == null) {
            articleQueryParams.setQueryPrivate(false);
        }

        PageResult<Article> articles = articleService.findArticle(articleQueryParams);
        model.addAttribute("articles", articles);


        Optional<Moment> latestMomentOp = momentService.getLatestMoment();
        latestMomentOp.ifPresent(moment -> model.addAttribute("moment", moment));

        return "index";
    }


    @GetMapping("article/{idOrAlias}")
    private String showArticleInfo(@PathVariable("idOrAlias") @NotBlank(message = "文章id或者别名不能为空") String idOrAlias, Model model) {
        if (StringUtils.isEmpty(idOrAlias)) {
            throw new ResourceNotFoundException(Message.ARTICLE_NOT_FOUND);
        }
        Article article = articleService.getArticleForView(idOrAlias);
        model.addAttribute(article);
        return "article";
    }

    /**
     * 获取归档页面数据
     * @param momentQueryParams momentQueryParams
     * @param model model
     * @return String
     */
    @GetMapping("moments")
    public String getMomentArchive(MomentQueryParams momentQueryParams, Model model) {
        if(momentQueryParams.getQueryPrivate() == null) {
            momentQueryParams.setQueryPrivate(false);
        }
        if(momentQueryParams.getStatus() == null) {
            momentQueryParams.setStatus(MomentStatus.POSTED);
        }
        PageResult<MomentArchiveDetail> archiveMoments = momentService.findArchiveMoments(momentQueryParams);
        model.addAttribute("archiveMoments", archiveMoments);
        return "moments";
    }


    @GetMapping("moment/{id}")
    public String getMoment(@PathVariable("id") Integer id, Model model) {
        MomentNav momentNav = momentService.findMomentNav(id);
        if(momentNav.getCurrentMoment() == null) {
            throw new ResourceNotFoundException(Message.MOMENT_NOT_EXISTS);
        }
        model.addAttribute("momentNav", momentNav);
        return "moment";
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
