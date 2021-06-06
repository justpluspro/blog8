//package com.qwli7.blog.web;
//
//import com.qwli7.blog.BlogProperties;
//import com.qwli7.blog.entity.Article;
//import com.qwli7.blog.entity.ArticleNav;
//import com.qwli7.blog.entity.Moment;
//import com.qwli7.blog.entity.MomentArchive;
//import com.qwli7.blog.entity.dto.PageDto;
//import com.qwli7.blog.entity.vo.ArticleQueryParam;
//import com.qwli7.blog.entity.vo.MomentQueryParam;
//import com.qwli7.blog.exception.ResourceNotFoundException;
//import com.qwli7.blog.service.ArticleService;
//import com.qwli7.blog.service.MomentService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//public class MainController {
//
//    private final ArticleService articleService;
//    private final MomentService momentService;
//    private final BlogProperties blogProperties;
//
//    public MainController(ArticleService articleService, MomentService momentService, BlogProperties blogProperties) {
//        this.articleService = articleService;
//        this.momentService = momentService;
//        this.blogProperties = blogProperties;
//    }
//
//    @GetMapping("/")
//    public String index(ArticleQueryParam queryParam, Model model) {
//        if(queryParam.hasNoSize()) {
//            queryParam.setSize(blogProperties.getDefaultPageSize());
//        }
//        final PageDto<Article> articlePage = articleService.selectPage(queryParam);
//        model.addAttribute("articlePage", articlePage);
//
//        MomentArchive momentArchive = momentService.selectLatestMoments();
//        model.addAttribute("latestMoments", momentArchive);
//
//        return "index";
//    }
//
//
//    @GetMapping("/article/{idOrAlias}")
//    public String article(@PathVariable("idOrAlias") String idOrAlias, Model model) {
//        final Optional<Article> articleOp = articleService.getArticle(idOrAlias);
//        if(!articleOp.isPresent()) {
//            throw new ResourceNotFoundException("article.notFound", "内容不存在");
//        }
//        model.addAttribute("article", articleOp.get());
//
//        final Optional<ArticleNav> articleNav = articleService.selectArticleNav(articleOp.get().getId());
//        articleNav.ifPresent(nav -> model.addAttribute("articleNav", nav));
//        return "article";
//    }
//
//    @GetMapping("moments")
//    public String moments(MomentQueryParam queryParam, Model model) {
//        if(queryParam.hasNoSize()) {
//            queryParam.setSize(blogProperties.getDefaultPageSize());
//        }
//        final PageDto<MomentArchive> archivePageDto = momentService.selectArchivePage(queryParam);
//        model.addAttribute("archivePageDto", archivePageDto);
//        return "moments";
//    }
//
//    @GetMapping("moment/{id}")
//    public String moment(@PathVariable("id") int id, Model model) {
//        model.addAttribute("moment", momentService.selectById(id));
//
//        return "moment";
//    }
//}
