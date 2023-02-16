package com.qwli7.blog.service.impl;

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

    public ArticleServiceImpl(ArticleDao articleDao, CategoryDao categoryDao) {
        this.articleDao = articleDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public void saveArticle(ArticleBean articleBean) {
        Integer categoryId = articleBean.getCategoryId();
        Optional<Category> categoryOptional = categoryDao.findById(categoryId);
        if(!categoryOptional.isPresent()) {
            throw new BizException(Message.CATEGORY_NOT_EXISTS);
        }
        Article article = new Article();
        article.setCategory(categoryOptional.get());
        article.setTitle(articleBean.getTitle());
        article.setContent(articleBean.getContent());
        article.setAlias(articleBean.getAlias());
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

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
        Integer page = articleQueryParams.getPage();
        Integer size = articleQueryParams.getSize();

        Sort sort = Sort.by(Sort.Order.desc("postedTime"));

        PageRequest pageRequest = PageRequest.of(page, size, sort);

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
    public void updateArticle() {

    }
}
