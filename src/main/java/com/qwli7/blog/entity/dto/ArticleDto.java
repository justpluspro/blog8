package com.qwli7.blog.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qwli7.blog.entity.Article;
import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.enums.ArticleState;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author qwli7 
 * @date 2023/2/16 18:09
 * 功能：blog8
 **/
public class ArticleDto implements Serializable {

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.alias = article.getAlias();
        this.content = article.getContent();
        this.state = article.getState();
        this.hits = article.getHits();
        this.comments = article.getComments();
        this.createTime = article.getCreateTime();
        this.postedTime = article.getPostedTime();
        this.modifiedTime = article.getModifiedTime();
        this.category = new CategoryDto(article.getCategory());

        Set<Tag> tags = article.getTags();
        List<TagDto> tagDtos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(tags)) {
            for(Tag tag: tags) {
                tagDtos.add(new TagDto(tag));
            }
        }
        this.setTagDtos(tagDtos);
    }

    private Integer id;

    private String title;

    private String content;

    private String digest;

    private String featureImage;

    private CategoryDto category;

    private ArticleState state;

    private Integer hits;

    private Integer comments;

    private String alias;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime postedTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedTime;

    private List<TagDto> tagDtos;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public List<TagDto> getTagDtos() {
        return tagDtos;
    }

    public void setTagDtos(List<TagDto> tagDtos) {
        this.tagDtos = tagDtos;
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
