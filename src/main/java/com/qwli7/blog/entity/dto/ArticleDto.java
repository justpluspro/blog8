package com.qwli7.blog.entity.dto;

import com.qwli7.blog.entity.Article;

import java.io.Serializable;

/**
 * @author qwli7 
 * @date 2023/2/16 18:09
 * 功能：blog8
 **/
public class ArticleDto implements Serializable {

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.category = new CategoryDto(article.getCategory());
    }

    private Integer id;

    private String title;

    private String content;

    private CategoryDto category;

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
