package com.qwli7.blog.entity.dto;

import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.enums.ArticleState;

import java.io.Serializable;
import java.time.LocalDateTime;

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
        this.state = article.getState();
        this.createTime = article.getCreateTime();
        this.postedTime = article.getPostedTime();
        this.category = new CategoryDto(article.getCategory());
    }

    private Integer id;

    private String title;

    private String content;

    private String digest;

    private String featureImage;

    private CategoryDto category;

    private ArticleState state;

    private LocalDateTime createTime;

    private LocalDateTime postedTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(LocalDateTime postedTime) {
        this.postedTime = postedTime;
    }

    public ArticleState getState() {
        return state;
    }

    public void setState(ArticleState state) {
        this.state = state;
    }

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

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getFeatureImage() {
        return featureImage;
    }

    public void setFeatureImage(String featureImage) {
        this.featureImage = featureImage;
    }
}
