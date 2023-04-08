package com.qwli7.blog.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.enums.ArticleStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author qwli7 
 * @date 2023/2/16 18:09
 * 功能：blog8
 **/
public class ArticleDetail implements Serializable {

    public ArticleDetail(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.alias = article.getAlias();
        this.content = article.getContent();
        this.status = article.getStatus();
        this.hits = article.getHits();
        this.comments = article.getComments();
        this.createTime = article.getCreateTime();
        this.postedTime = article.getPostedTime();
        this.modifiedTime = article.getModifiedTime();
        this.category = new CategoryDto(article.getCategory());

    }

    private Integer id;

    private String title;

    private String content;

    private String digest;

    private String featureImage;

    private CategoryDto category;

    private ArticleStatus status;

    private Integer hits;

    private Integer comments;

    private String alias;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postedTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedTime;


    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

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

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
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
