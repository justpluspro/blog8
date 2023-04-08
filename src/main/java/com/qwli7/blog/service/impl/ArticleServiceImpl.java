package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.ArticleTag;
import com.qwli7.blog.entity.Category;
import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.dto.ArticleDetail;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.enums.ArticleStatus;
import com.qwli7.blog.entity.vo.ArticleQueryParams;
import com.qwli7.blog.entity.vo.HandledArticleQueryParams;
import com.qwli7.blog.exception.BizException;
import com.qwli7.blog.exception.Message;
import com.qwli7.blog.mapper.ArticleMapper;
import com.qwli7.blog.mapper.ArticleTagMapper;
import com.qwli7.blog.mapper.CategoryMapper;
import com.qwli7.blog.mapper.TagMapper;
import com.qwli7.blog.plugin.md.MarkdownHandler;
import com.qwli7.blog.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author qwli7
 * @date 2023/2/16 15:00
 * 功能：blog8
 **/
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleMapper articleMapper;

    private final CategoryMapper categoryMapper;

    private final ArticleTagMapper articleTagMapper;

    private final TagMapper tagMapper;
    private final MarkdownHandler markdownHandler;


    public ArticleServiceImpl(ArticleMapper articleMapper, CategoryMapper categoryMapper,
                              ArticleTagMapper articleTagMapper, TagMapper tagMapper,
                              MarkdownHandler markdownHandler) {
        this.articleMapper = articleMapper;
        this.categoryMapper = categoryMapper;
        this.articleTagMapper = articleTagMapper;
        this.tagMapper = tagMapper;
        this.markdownHandler = markdownHandler;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void addArticle(Article article) {
        Category category = article.getCategory();
        if (category == null) {
            throw new BizException(Message.CATEGORY_NOT_EXISTS);
        }
        Integer categoryId = category.getId();
        Optional<Category> categoryOptional = categoryMapper.findById(categoryId);
        if (!categoryOptional.isPresent()) {
            throw new BizException(Message.CATEGORY_NOT_EXISTS);
        }

        String alias = article.getAlias();
        Optional<Article> articleOp = articleMapper.findByAlias(alias);
        if (articleOp.isPresent()) {
            throw new BizException(Message.ARTICLE_ALIAS_EXISTS);
        }

        Boolean privateArticle = article.getPrivateArticle();
        if(privateArticle == null){
            article.setPrivateArticle(false);
        }
        Boolean allowComment = article.getAllowComment();
        if(allowComment == null) {
            article.setAllowComment(true);
        }


        article.setHits(0);
        article.setComments(0);
        ArticleStatus articleStatus = article.getStatus();
        if (ArticleStatus.POSTED.equals(articleStatus)) {
            article.setPostedTime(LocalDateTime.now());
        }
        articleMapper.insert(article);

        Set<Tag> tags = article.getTags();
        if (!CollectionUtils.isEmpty(tags)) {
            List<ArticleTag> articleTagList = new ArrayList<>();
            for (Tag tag : tags) {
                String tagName = tag.getTagName();
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticle(article);
                Optional<Tag> tagOptional = tagMapper.findByName(tagName);
                if (!tagOptional.isPresent()) {
                    tagMapper.insert(tag);
                    articleTag.setTag(tag);
                } else {
                    articleTag.setTag(tagOptional.get());
                }
                articleTagList.add(articleTag);
            }
            articleTagMapper.batchInsert(articleTagList);
        }
    }

    @Override
    public PageResult<ArticleDetail> queryArticle(ArticleQueryParams articleQueryParams) {


        return null;
    }

    @Override
    public PageResult<Article> findArticle(ArticleQueryParams articleQueryParams) {
        HandledArticleQueryParams handledArticleQueryParams = new HandledArticleQueryParams(articleQueryParams);
        Integer categoryId = articleQueryParams.getCategoryId();
        if(categoryId != null) {
            Optional<Category> categoryOptional = categoryMapper.findById(categoryId);
            if(!categoryOptional.isPresent()) {
                return new PageResult<>(articleQueryParams, 0, new ArrayList<>());
            }
            handledArticleQueryParams.setCategory(categoryOptional.get());
        }

        Integer count = articleMapper.count(handledArticleQueryParams);
        if(count == null || count == 0) {
            return new PageResult<>(articleQueryParams, 0, new ArrayList<>());
        }

        List<Article> articles = articleMapper.findArticle(handledArticleQueryParams);
        if(CollectionUtils.isEmpty(articles)) {
            return new PageResult<>(articleQueryParams, 0, new ArrayList<>());
        }
        for(Article article: articles) {
            String digest = article.getDigest();
        }

        return new PageResult<>(articleQueryParams, count, articles);

    }

    @Override
    public void updateArticle(Article article) {
        Integer id = article.getId();

        Optional<Article> articleOptional = articleMapper.findById(article.getId());
        if (!articleOptional.isPresent()) {
            throw new BizException(Message.ARTICLE_NOT_EXISTS);
        }
        Article dbArticle = articleOptional.get();

    }

    @Override
    public Article getArticleForView(String idOrAlias) {
        Integer id = null;
        try {
            id = Integer.parseInt(idOrAlias);
        } catch (NumberFormatException e) {
            //ignored
        }
        Optional<Article> articleOp;
        if (id == null) {
            articleOp = articleMapper.findByAlias(idOrAlias);
        } else {
            articleOp = articleMapper.findById(id);
        }
        if (!articleOp.isPresent()) {
            throw new BizException(Message.ARTICLE_NOT_FOUND);
        }
        Article article = articleOp.get();

        String content = article.getContent();
        String htmlContent = markdownHandler.toHtml(content);
        article.setContent(htmlContent);

        return article;
    }


    @Override
    public Optional<Article> getArticleForEdit(Integer id) {
        return articleMapper.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteArticle(Integer id) {
        Article article = articleMapper.findById(id).orElseThrow(()
                -> new BizException(Message.ARTICLE_NOT_EXISTS));
        articleMapper.deleteById(id);
        articleTagMapper.deleteByArticle(article);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void hitArticle(Integer id) {
        Article article = articleMapper.findById(id).orElseThrow(() -> new BizException(Message.ARTICLE_NOT_EXISTS));

        ArticleStatus status = article.getStatus();
        if (!ArticleStatus.POSTED.equals(status)) {
            throw new BizException(Message.ARTICLE_NOT_EXISTS);
        }

        Article update = new Article();
        update.setId(article.getId());
        update.setHits(article.getHits() + 1);
        articleMapper.updateHits(update);
    }
}
