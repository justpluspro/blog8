package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.*;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.ArticleQueryParam;
import com.qwli7.blog.event.ArticlePostEvent;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.mapper.ArticleMapper;
import com.qwli7.blog.mapper.ArticleTagMapper;
import com.qwli7.blog.mapper.CategoryMapper;
import com.qwli7.blog.mapper.TagMapper;
import com.qwli7.blog.service.ArticleService;
import com.qwli7.blog.service.CommentModuleHandler;
import com.qwli7.blog.service.Markdown2Html;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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
    private final TagMapper tagMapper;
    private final Markdown2Html markdown2Html;
    private final ApplicationEventPublisher publisher;

    public ArticleServiceImpl(Markdown2Html markdown2Html, ArticleMapper articleMapper,
                              CategoryMapper categoryMapper, ArticleTagMapper articleTagMapper,
                              TagMapper tagMapper, ApplicationEventPublisher publisher) {
        this.markdown2Html = markdown2Html;
        this.articleMapper = articleMapper;
        this.articleTagMapper = articleTagMapper;
        this.categoryMapper = categoryMapper;
        this.tagMapper = tagMapper;
        this.publisher = publisher;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ArticleSaved save(Article article) {
        article.setHits(0);
        article.setComments(0);
        final Category category = article.getCategory();
        if(category != null && category.getId() != null) {
            final Optional<Category> categoryOp = categoryMapper.findById(category.getId());
            if(!categoryOp.isPresent()) {
                throw new LogicException("category.notExists", "分类不存在");
            }
        }
        final String alias = article.getAlias();
        if(!StringUtils.isEmpty(alias)) {
            Optional<Article> articleOp = articleMapper.findByAlias(alias);
            if(articleOp.isPresent()) {
                throw new LogicException("alias.exists", "别名已经存在");
            }
        }

        article.setCreateAt(LocalDateTime.now());

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
        }

        articleMapper.insert(article);
        processArticleTags(article);

        publisher.publishEvent(new ArticlePostEvent(this, article));

        return new ArticleSaved(article.getId(), true);
    }

    @Override
    public PageDto<Article> selectPage(ArticleQueryParam queryParam) {
        return null;
    }

    @Override
    public void deleteById(int id) {
//        articleMapper.select
    }

    private void processArticleTags(Article article) {
        final List<Tag> tags = article.getTags();
        if(tags == null || tags.size() == 0) {
            return;
        }
        if(tags.size() > 5) {
            throw new LogicException("max.tags", "最多标签不能超过 5 个");
        }
        for(Tag tag: tags) {
            final Optional<Tag> tagOp = tagMapper.findById(tag.getId());
            if(tagOp.isPresent()) {
//                tag
            }
        }
    }




    @Override
    public String getModuleName() {
        return Article.class.getSimpleName().toLowerCase();
    }


}
