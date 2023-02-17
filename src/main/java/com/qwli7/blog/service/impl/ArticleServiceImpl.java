package com.qwli7.blog.service.impl;

import com.qwli7.blog.BlogContext;
import com.qwli7.blog.dao.ArticleDao;
import com.qwli7.blog.dao.CategoryDao;
import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.Category;
import com.qwli7.blog.entity.dto.ArticleDto;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.enums.ArticleState;
import com.qwli7.blog.entity.vo.ArticleBean;
import com.qwli7.blog.entity.vo.ArticleQueryParams;
import com.qwli7.blog.exception.BizException;
import com.qwli7.blog.exception.Message;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.plugin.md.MarkdownHandler;
import com.qwli7.blog.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author qwli7 
 * @date 2023/2/16 15:00
 * 功能：blog8
 **/
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDao articleDao;

    private final CategoryDao categoryDao;

    private final MarkdownHandler markdownHandler;

    public ArticleServiceImpl(ArticleDao articleDao, CategoryDao categoryDao, MarkdownHandler markdownHandler) {
        this.articleDao = articleDao;
        this.categoryDao = categoryDao;
        this.markdownHandler = markdownHandler;
    }

    @Override
    public void saveArticle(ArticleBean articleBean) {
        Integer categoryId = articleBean.getCategoryId();
        Optional<Category> categoryOptional = categoryDao.findById(categoryId);
        if(!categoryOptional.isPresent()) {
            throw new BizException(Message.CATEGORY_NOT_EXISTS);
        }

        String alias = articleBean.getAlias();
        Optional<Article> articleOp = articleDao.findArticleByAlias(alias);
        if(articleOp.isPresent()) {
            throw new BizException(Message.ARTICLE_ALIAS_EXISTS);
        }

        Article article = new Article();
        article.setHits(0);
        article.setComments(0);
        article.setCategory(categoryOptional.get());
        article.setTitle(articleBean.getTitle());
        article.setContent(articleBean.getContent());
        article.setAlias(alias);
        ArticleState articleState = articleBean.getArticleState();
        article.setState(articleState);
        if(ArticleState.POSTED.equals(articleState)) {
            article.setPostedTime(LocalDateTime.now());
        }
        articleDao.save(article);
    }

    @Override
    public PageResult<ArticleDto> queryArticle(ArticleQueryParams articleQueryParams) {


        Specification<Article> specification = new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                Path<Object> title = root.get("title");
                Path<Object> content = root.get("content");
                Path<Object> alias = root.get("alias");

                Path<Object> state = root.get("state");

                List<Predicate> predicates = new ArrayList<>();

                //左连接 category 表
                Join<Object, Object> category = root.join("category", JoinType.LEFT);

//                Expression<Integer> categoryPredicate = ;
//                Path<Object> categoryId = (Path<Object>) categoryPredicate;


                Predicate titlePredicate = criteriaBuilder.like(title.as(String.class), "%s"+articleQueryParams.getQuery()+"%s");
                Predicate contentPredicate = criteriaBuilder.like(content.as(String.class), "%s"+articleQueryParams.getQuery()+"%s");
                Predicate aliasPredicate = criteriaBuilder.like(alias.as(String.class), "%s"+articleQueryParams.getQuery()+"%s");


                Predicate statePredicate = criteriaBuilder.equal(state, ArticleState.class);

//                Predicate categoryIdPredicate = criteriaBuilder.equal(categoryId, )

                Predicate predicate = criteriaBuilder.or(titlePredicate, contentPredicate, aliasPredicate);
//                Predicate predicate2 = criteriaBuilder.and(statePredicate);
                Predicate predicate3 = criteriaBuilder.and(criteriaBuilder.equal(category.get("id").as(Integer.class),
                        articleQueryParams.getCategoryId()));
                predicates.add(predicate);
//                predicates.add(predicate2);
                predicates.add(predicate3);

                return criteriaBuilder.and(new ArrayList<>().toArray(new Predicate[0]));
            }
        };
        Integer page = articleQueryParams.getPage();
        Integer size = articleQueryParams.getSize();

        Sort sort = Sort.by(Sort.Order.desc("postedTime"));

        PageRequest pageRequest = PageRequest.of(page-1, size, sort);

        Page<Article> articlePage = articleDao.findAll(specification, pageRequest);
        int totalPages = articlePage.getTotalPages();
        long totalElements = articlePage.getTotalElements();
        List<Article> articles = articlePage.getContent();
//        List<Article> articles = (List<Article>) articlePage
        if(CollectionUtils.isEmpty(articles)) {
            return new PageResult<>(articleQueryParams, 0, new ArrayList<>());
        }
        List<ArticleDto> articleDtos = articles.stream().map(ArticleDto::new).collect(Collectors.toList());

        return new PageResult<>(articleQueryParams, totalElements, articleDtos);
    }

    @Override
    public void updateArticle(ArticleBean articleBean) {
        Integer id = articleBean.getId();
        Optional<Article> articleOp = articleDao.findById(id);
        if(!articleOp.isPresent()) {
            throw new BizException(Message.ARTICLE_NOT_EXISTS);
        }
        Integer categoryId = articleBean.getCategoryId();
        Optional<Category> categoryOp = categoryDao.findById(categoryId);
        if(!categoryOp.isPresent()) {
            throw new BizException(Message.CATEGORY_NOT_EXISTS);
        }
        Category category = categoryOp.get();
        Article article = articleOp.get();
        article.setTitle(articleBean.getTitle());
        article.setContent(articleBean.getContent());
        article.setAlias(articleBean.getAlias());
        article.setState(articleBean.getArticleState());
        article.setCategory(category);

        articleDao.save(article);

    }

    @Override
    public ArticleDto getArticleForView(String idOrAlias) {
        Integer id = null;
        try {
            id = Integer.parseInt(idOrAlias);
        } catch (NumberFormatException e) {
            // ignored
        }
        Optional<Article> articleOp;
        if(id == null) {
            //别名
            articleOp = articleDao.findArticleByAlias(idOrAlias);
        } else {
            if(id <= 0) {
                throw new ResourceNotFoundException(Message.ARTICLE_NOT_FOUND);
            } else {
                articleOp = articleDao.findById(id);
            }
        }
        if(!articleOp.isPresent()) {
            throw new ResourceNotFoundException(Message.ARTICLE_NOT_FOUND);
        }
        Article article = articleOp.get();
        ArticleState state = article.getState();
        if(state.equals(ArticleState.DRAFT) && !BlogContext.isAuthorized()) {
            //状态为草稿并且未登录
            throw new ResourceNotFoundException(Message.ARTICLE_NOT_FOUND);
        }
        ArticleDto articleDto = new ArticleDto(article);
        String content = articleDto.getContent();
        content = markdownHandler.toHtml(content);
        articleDto.setContent(content);

        return articleDto;
    }


    @Override
    public Optional<Article> getArticleForEdit(Integer id) {
        return articleDao.findById(id);
    }
}
