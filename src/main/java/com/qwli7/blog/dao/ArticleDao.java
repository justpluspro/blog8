package com.qwli7.blog.dao;

import com.qwli7.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author qwli7 
 * @date 2023/2/16 15:00
 * 功能：blog8
 **/
@Repository
public interface ArticleDao extends PagingAndSortingRepository<Article, Integer>, JpaSpecificationExecutor<Article> {
}
