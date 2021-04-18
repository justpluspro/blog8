package com.qwli7.blog.service.impl;

import com.qwli7.blog.BlogContext;
import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.entity.*;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.ArticleQueryParam;
import com.qwli7.blog.entity.vo.HandledArticleQueryParam;
import com.qwli7.blog.event.ArticleBatchDeleteEvent;
import com.qwli7.blog.event.ArticleDeleteEvent;
import com.qwli7.blog.event.ArticlePostEvent;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.mapper.*;
import com.qwli7.blog.queue.runnable.ArticlePostRunnable;
import com.qwli7.blog.service.ArticleIndexer;
import com.qwli7.blog.service.ArticleService;
import com.qwli7.blog.service.CommentModuleHandler;
import com.qwli7.blog.service.Markdown2Html;
import com.qwli7.blog.util.JsoupUtil;
import com.qwli7.blog.util.TimeUtils;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：ArticleService
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
    private final ScheduledExecutorService scheduledExecutorService;
    private ArticleIndexer articleIndexer;
    private final ApplicationEventPublisher publisher;

    public ArticleServiceImpl(Markdown2Html markdown2Html, ArticleMapper articleMapper,
                              CategoryMapper categoryMapper, ArticleTagMapper articleTagMapper,
                              TagMapper tagMapper, CommentMapper commentMapper,
                              ScheduledExecutorService scheduledExecutorService,
                              BlogProperties blogProperties,
                              ApplicationEventPublisher publisher) {
        this.markdown2Html = markdown2Html;
        this.articleMapper = articleMapper;
        this.articleTagMapper = articleTagMapper;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.commentMapper = commentMapper;
        this.blogProperties = blogProperties;
        this.scheduledExecutorService = scheduledExecutorService;
        try {
            this.articleIndexer = new ArticleIndexer();
        } catch (IOException ex){
            ex.printStackTrace();
            System.out.println("闯建索引文件失败");
        }
        this.publisher = publisher;
    }

    /**
     * 保存文章
     * @param article article
     * @return ArticleSaved
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ArticleSaved save(Article article) {
        article.setHits(0);
        article.setComments(0);
        article.setCreateAt(LocalDateTime.now());

        Set<Tag> tags = article.getTags();
        int maxArticleTagSize = blogProperties.getMaxArticleTagCount();
        if(tags != null && tags.size() > maxArticleTagSize) {
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
                    final long seconds = TimeUtils.getSeconds(postAt, LocalDateTime.now());
                    if(seconds <= 0) {
                        throw new LogicException("scheduled.error", "预计发布文章错误");
                    }
                    ArticlePostRunnable articlePostRunnable = new ArticlePostRunnable(article);
                    scheduledExecutorService.schedule(articlePostRunnable, seconds, TimeUnit.SECONDS);
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
        processArticleTagsAfterInsertOrUpdate(article);

        if(ArticleStatus.POST == article.getStatus()) {
            publisher.publishEvent(new ArticlePostEvent(this, article));
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                try {
                    articleIndexer.addIndex(article);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        return new ArticleSaved(article.getId(), true);
    }

    /**
     * 更新文章
     * @param article article
     */
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
        processArticleTagsAfterInsertOrUpdate(article);
        articleMapper.update(article);

        if(article.getStatus().equals(ArticleStatus.POST)) {
            //重构索引
        }
        //事务提交之后，需要删除之前的文档

    }

    /**
     * 分页查询文章
     * @param queryParam queryParam
     * @return PageDto
     */
    @Transactional(readOnly = true)
    @Override
    public PageDto<Article> selectPage(ArticleQueryParam queryParam) {
        final Integer categoryId = queryParam.getCategoryId();
        Category category;
        HandledArticleQueryParam handledArticleQueryParam = new HandledArticleQueryParam();
        if(categoryId != null && categoryId > 0) {
            final Optional<Category> categoryOp = categoryMapper.findById(categoryId);
            if(categoryOp.isPresent()) {
                category = categoryOp.get();
                handledArticleQueryParam.setCategory(category);
            } else {
                return new PageDto<>(queryParam, 0, new ArrayList<>());
            }
        }

        if(!BlogContext.isAuthenticated()) {
            handledArticleQueryParam.setStatuses(Collections.singletonList(ArticleStatus.POST));
        }

        final String query = queryParam.getQuery();
        if(!StringUtils.isEmpty(query)) {
            try {
                List<Integer> ids = articleIndexer.doSearch(handledArticleQueryParam);
                if(!CollectionUtils.isEmpty(ids)) {
                    handledArticleQueryParam.setAids(ids);
                }
            } catch (IOException | ParseException ex){
                ex.printStackTrace();
            }
        }

        int count = articleMapper.count(handledArticleQueryParam);
        if(count == 0) {
            return new PageDto<>(queryParam, 0, new ArrayList<>());
        }

        handledArticleQueryParam.setPage(queryParam.getPage());
        handledArticleQueryParam.setSize(queryParam.getSize());
        handledArticleQueryParam.setStart(queryParam.getStart());
        handledArticleQueryParam.setOffset(queryParam.getSize());
        List<Article> articles = articleMapper.selectPage(handledArticleQueryParam);

        processArticles(articles);
        processContents(articles);

        return new PageDto<>(queryParam, count, articles);
    }

    /**
     * 删除文章
     * @param id id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteById(int id) {
        final Optional<Article> articleOp = articleMapper.selectById(id);
        if(!articleOp.isPresent()) {
            throw new ResourceNotFoundException("article.notExists", "内容未找到");
        }
        final CommentModule commentModule = new CommentModule(articleOp.get().getId(), getModuleName());
        commentMapper.deleteByModule(commentModule);
        articleTagMapper.deleteByArticle(articleOp.get());
        articleMapper.deleteById(articleOp.get().getId());
        publisher.publishEvent(new ArticleDeleteEvent(this, articleOp.get()));
    }

    /**
     * 获取文章为编辑
     * @param id id
     * @return Article
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<Article> getArticleForEdit(int id) {
        return articleMapper.selectById(id);
    }


    /**
     * 获取文章
     * @param idOrAlias id 或者别名
     * @return Article
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<Article> getArticle(String idOrAlias) {
        Integer id = null;
        try{
            id = Integer.parseInt(idOrAlias);
        } catch (NumberFormatException ignored) {
            // ignored this exception
        }
        Optional<Article> articleOp;
        if(id == null) {
            articleOp = articleMapper.selectByAlias(idOrAlias);
        } else {
            articleOp = articleMapper.selectById(id);
        }
        if(!articleOp.isPresent()) {
            throw new ResourceNotFoundException("article.notExists", "文章不存在");
        }
        final Article article = articleOp.get();
        final ArticleStatus status = article.getStatus();

        // 未登录情况下
        if(!BlogContext.isAuthenticated()) {
            // 非发布状态  || 私人动态不允许访问
            if (!ArticleStatus.POST.equals(status) || article.getPrivate() == null || article.getPrivate()) {
                throw new LogicException("invalid.articleStatus", "无效的状态");
            }
        }
        processArticles(Collections.singletonList(article));
        processContent(article);
        return Optional.of(article);
    }

    /**
     * 获取文章导航
     * @param id id
     * @return ArticleNav
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<ArticleNav> selectArticleNav(int id) {
        return Optional.empty();
    }


    @Transactional(readOnly = true)
    @Override
    public void hits(int id) {
        final Article article = articleMapper.selectById(id).orElseThrow(()
                -> new ResourceNotFoundException("article.notExists", "文章不存在"));
        // 登录情况下不统计点击量
        if(!BlogContext.isAuthenticated()) {
            return;
        }
        articleMapper.addHits(id, article.getHits() + 1);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteByIds(List<Integer> ids) {
        List<Article> articles = articleMapper.selectByIds(ids);
        if(articles.isEmpty()) {
            return;
        }
        publisher.publishEvent(new ArticleBatchDeleteEvent(this, articles));
    }

    /**
     * 处理文章 | 主要是处理标签
     * @param articles article
     */
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

    /**
     * 处理内容
     * @param article article
     */
    private void processContent(Article article) {
        final String content = article.getContent();
        final String featureImage = article.getFeatureImage();
        if(StringUtils.isEmpty(featureImage)) {
            JsoupUtil.getFirstImage(markdown2Html.toHtml(content)).ifPresent(article::setFeatureImage);
        }
        article.setContent(markdown2Html.toHtml(content));
    }


    /**
     * 处理内容
     * 1. md -》 html
     * 2. 如果没有 featureImage，则从内容中抽取图片作为 featureImage
     * @param articles articles
     */
    private void processContents(List<Article> articles) {
        if(CollectionUtils.isEmpty(articles)) {
            return;
        }
        Map<Integer, String> markdownMap = articles.stream().filter(e -> e.getContent() != null)
                .collect(Collectors.toMap(Article::getId, Article::getContent));
        markdown2Html.toHtmls(markdownMap);

        articles.forEach(e -> {
            final String featureImage = e.getFeatureImage();
            if(!StringUtils.isEmpty(featureImage)) {
                JsoupUtil.getFirstImage(markdownMap.get(e.getId())).ifPresent(e::setFeatureImage);
            }
            e.setContent(markdownMap.get(e.getId()));
        });

    }


    /**
     * 处理文章标签
     * @param article article
     */
    private void processArticleTagsAfterInsertOrUpdate(Article article) {
        articleTagMapper.deleteByArticle(article);
        List<ArticleTag> articleTags = new ArrayList<>();
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
//            articleTagMapper.insert(new ArticleTag(article.getId(), oldTag.getId()));
            articleTags.add(new ArticleTag(article.getId(), oldTag.getId()));
        }
        articleTagMapper.batchInsert(articleTags);
    }

    /**
     * 插入评论前判断文章状态
     * @param module module
     */
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

        // 发布状态的文章，私人状态并且登录的情况下才能评论
        if(ArticleStatus.POST.equals(status)) {
            if(article.getPrivate() != null && article.getPrivate() && !BlogContext.isAuthenticated()) {
                throw new LogicException("operation.notAllowed", "不允许评论私人动态");
            }
        }

        // 非发布状态并且未登录的情况下不能评论
        if(!ArticleStatus.POST.equals(status) && !BlogContext.isAuthenticated()) {
            //非发布状态，并且未登录
            throw new LogicException("operation.notAllowed", "操作不允许");
        }
    }

    /**
     * 查询评论前校验
     * @param module module
     */
    @Override
    public void validateBeforeQuery(CommentModule module) {
        Assert.notNull(module, "module cannot be null");
        if(!getModuleName().equals(module.getName())) {
            throw new LogicException("illegal.operators", "无效的操作");
        }

        final Optional<Article> articleOp = articleMapper.selectById(module.getId());
        if(!articleOp.isPresent()) {
            throw new ResourceNotFoundException("article.notExists", "内容不存在");
        }


    }

    /**
     * 模块名称
     * @return String
     */
    @Override
    public String getModuleName() {
        return Article.class.getSimpleName().toLowerCase();
    }
}
