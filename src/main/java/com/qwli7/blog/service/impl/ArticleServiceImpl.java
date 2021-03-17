package com.qwli7.blog.service.impl;

import com.qwli7.blog.BlogContext;
import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.entity.*;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.ArticleQueryParam;
import com.qwli7.blog.entity.vo.HandledArticleQueryParam;
import com.qwli7.blog.event.ArticleDeleteEvent;
import com.qwli7.blog.event.ArticlePostEvent;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.mapper.*;
import com.qwli7.blog.service.ArticleService;
import com.qwli7.blog.service.CommentModuleHandler;
import com.qwli7.blog.service.Markdown2Html;
import com.qwli7.blog.template.helper.Jsoups;
import com.qwli7.blog.util.JsoupUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.stream.Collectors;


/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
@Service
public class ArticleServiceImpl implements ArticleService, CommentModuleHandler {

    private final ArticleMapper articleMapper;
    private final CategoryMapper categoryMapper;
    private final ArticleTagMapper articleTagMapper;
    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;
    private final Markdown2Html markdown2Html;
    private final BlogProperties blogProperties;
    private final ApplicationEventPublisher publisher;

    public ArticleServiceImpl(Markdown2Html markdown2Html, ArticleMapper articleMapper,
                              CategoryMapper categoryMapper, ArticleTagMapper articleTagMapper,
                              TagMapper tagMapper, CommentMapper commentMapper,
                              BlogProperties blogProperties,
                              ApplicationEventPublisher publisher) {
        this.markdown2Html = markdown2Html;
        this.articleMapper = articleMapper;
        this.articleTagMapper = articleTagMapper;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.commentMapper = commentMapper;
        this.blogProperties = blogProperties;
        this.publisher = publisher;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ArticleSaved save(Article article) {
        article.setHits(0);
        article.setComments(0);
        article.setCreateAt(LocalDateTime.now());

        Set<Tag> tags = article.getTags();
        if(tags != null && tags.size() > 5) {
            throw new LogicException("tags.exceed.limit", "标签最多不能超过5个");
        }

        final Category category = article.getCategory();
        if(category != null && category.getId() != null) {
            final Optional<Category> categoryOp = categoryMapper.findById(category.getId());
            if(!categoryOp.isPresent()) {
                throw new LogicException("category.notExists", "分类不存在");
            }
            article.setCategory(categoryOp.get());
        }
        final String alias = article.getAlias();
        if(!StringUtils.isEmpty(alias)) {
            Optional<Article> articleOp = articleMapper.selectByAlias(alias);
            if(articleOp.isPresent()) {
                throw new LogicException("alias.exists", "别名已经存在");
            }
        }

        ArticleStatus status = article.getStatus();
        if(status == null ) {
            status = ArticleStatus.POST;
        }
        article.setStatus(status);

        switch (status) {
            case DRAFT:
                article.setModifyAt(LocalDateTime.now());
                break;
            case POST:
                article.setModifyAt(LocalDateTime.now());
                article.setPostAt(LocalDateTime.now());
                break;
            case SCHEDULED:
                LocalDateTime postAt = article.getPostAt();
                if(postAt == null || postAt.isBefore(LocalDateTime.now())) {
                    article.setPostAt(LocalDateTime.now());
                    article.setModifyAt(LocalDateTime.now());
                    article.setStatus(ArticleStatus.POST);
                } else {
                    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
//                    exe
//                    cutorService.scheduleWithFixedDelay()
                }
                break;
            default:
                throw new LogicException("illegal.status", "非法状态");
        }

        String featureImage = article.getFeatureImage();
        if(StringUtils.isEmpty(featureImage)) {
            String html = markdown2Html.toHtml(article.getContent());
            JsoupUtil.getFirstImage(html).ifPresent(article::setFeatureImage);
        }

        articleMapper.insert(article);
        processArticleTags(article);

        publisher.publishEvent(new ArticlePostEvent(this, article));

        return new ArticleSaved(article.getId(), true);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LogicException.class)
    @Override
    public void update(Article article) {

        Optional<Article> articleOp = articleMapper.selectById(article.getId());
        if(!articleOp.isPresent()) {
            throw new ResourceNotFoundException("article.notFound", "文章未找到");
        }
        String alias = article.getAlias();
        if(!StringUtils.isEmpty(alias)) {
            Optional<Article> oldOp = articleMapper.selectByAlias(alias);
            if(oldOp.isPresent() && !oldOp.get().getId().equals(article.getId())) {
                throw new LogicException("article.alias.exists", "文章别名已被使用");
            }
        }
        Article oldArticle = articleOp.get();

        article.setModifyAt(LocalDateTime.now());
        article.setCreateAt(LocalDateTime.now());

        if(article.getStatus().equals(ArticleStatus.POST)) {
            article.setPostAt(oldArticle.getPostAt());
            if(article.getPostAt() == null || article.getPostAt().isAfter(LocalDateTime.now())) {
                article.setPostAt(LocalDateTime.now());
            }
        } else if(article.getStatus().equals(ArticleStatus.DRAFT)) {
            article.setPostAt(oldArticle.getPostAt());
        }
        processArticleTags(article);
        articleMapper.update(article);

        if(article.getStatus().equals(ArticleStatus.POST)) {
            //重构索引
        }
        //事务提交之后，需要删除之前的文档

    }

    @Override
    public PageDto<Article> selectPage(ArticleQueryParam queryParam) {
//        final Integer categoryId = queryParam.getCategoryId();
//        Category category = null;
//        if(categoryId != null && categoryId > 0) {
//            final Optional<Category> categoryOp = categoryMapper.findById(categoryId);
//            if(categoryOp.isPresent()) {
//                category = categoryOp.get();
//            }
//        }
//        if(category == null) {
//            return new PageDto<>(queryParam, 0, new ArrayList<>());
//        }

        int count = articleMapper.count(queryParam);
        if(count == 0) {
            return new PageDto<>(queryParam, 0, new ArrayList<>());
        }

        HandledArticleQueryParam handledArticleQueryParam = new HandledArticleQueryParam();
        handledArticleQueryParam.setPage(queryParam.getPage());
        handledArticleQueryParam.setSize(queryParam.getSize());
        handledArticleQueryParam.setStart(0);
        handledArticleQueryParam.setOffset(queryParam.getSize());
        List<Article> articles = articleMapper.selectPage(handledArticleQueryParam);

        processArticles(articles);


        return new PageDto<>(queryParam, count, articles);
    }

    private void processArticles(List<Article> articles) {
        if(CollectionUtils.isEmpty(articles)) {
            return;
        }
        for(Article article: articles) {
            Set<Tag> tags = article.getTags();
            if(CollectionUtils.isEmpty(tags)) {
                continue;
            }
            article.setTags(tags.stream().map(Tag::getId).map(tagMapper::findById).filter(Optional::isPresent)
                    .map(Optional::get).collect(Collectors.toCollection(HashSet::new)));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteById(int id) {
        final Optional<Article> articleOp = articleMapper.selectById(id);
        if(!articleOp.isPresent()) {
            return;
        }
        final CommentModule commentModule = new CommentModule(articleOp.get().getId(), getModuleName());
        commentMapper.deleteByModule(commentModule);
        articleTagMapper.deleteByArticle(articleOp.get());
        articleMapper.deleteById(articleOp.get().getId());
        publisher.publishEvent(new ArticleDeleteEvent(this, articleOp.get()));
        //        articleMapper.select
    }

    private void processArticleTags(Article article) {
        articleTagMapper.deleteByArticle(article);
        for(Tag tag: article.getTags()) {
            String name = tag.getName();
            name = StringUtils.trimAllWhitespace(name);
            Optional<Tag> tagOp = tagMapper.findByName(name);
            Tag oldTag;
            if(tagOp.isPresent()){
                oldTag = tagOp.get();
            } else {
                oldTag = new Tag();
                oldTag.setName(name);
                oldTag.setCreateAt(LocalDateTime.now());
                oldTag.setModifyAt(LocalDateTime.now());
                tagMapper.insert(oldTag);
            }
            articleTagMapper.insert(new ArticleTag(article.getId(), oldTag.getId()));
        }
    }


    @Override
    public void validateBeforeInsert(CommentModule module) {
        if(module == null) {
            throw new LogicException("invalid.module", "模块不能为空");
        }
        final Optional<Article> articleOp = articleMapper.selectById(module.getId());
        if(!articleOp.isPresent()) {
            throw new ResourceNotFoundException("article.notExists", "内容不存在");
        }
        final Article article = articleOp.get();

        final Boolean allowComment = article.getAllowComment();
        if(allowComment != null && !allowComment) {
            throw new LogicException("comment.notAllowed", "评论已关闭");
        }

        final ArticleStatus status = article.getStatus();


        if(ArticleStatus.POST.equals(status)) {
            if(article.getPrivate() != null && article.getPrivate() && !BlogContext.isAuthenticated()) {
                throw new LogicException("operation.notAllowed", "不允许评论私人动态");
            }
        }



        if(!ArticleStatus.POST.equals(status) && !BlogContext.isAuthenticated()) {
            //非发布状态，并且未登录
            throw new LogicException("operation.notAllowed", "操作不允许");
        }
    }

    @Override
    public String getModuleName() {
        return Article.class.getSimpleName().toLowerCase();
    }


}
