package com.qwli7.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author qwli7
 * 2021/2/22 13:05
 * 功能：Article
 **/
public class Article extends BaseEntity implements Serializable {

    @NotBlank(message = "标题不能为空")
    @Length(max = 128, min = 1, message = "标题长度不能超过 {max}")
    private String title ;

    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 摘要
     */
    @Length(max = 2048, message = "摘要长度不能超过 {max}")
    private String summary;

    /**
     * 别名
     * 这里用正则表达式校验
     */
    @Pattern(regexp = "^[A-Za-z]+$", message = "别名仅仅只能由英文字母组成")
    private String alias;

    /**
     * 点击量
     */
    private Integer hits;

    /**
     * 评论
     */
    private Integer comments;

    /**
     * 文章状态
     */
    private ArticleStatus status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyAt;

    /**
     * 发布时间
     * 如果给了，需要判断发布时间必须在当前时间之后
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent(message = "发布时间不能是过去的时间")
    private LocalDateTime postAt;

    /**
     * 分类
     */
    private Category category;

    /**
     * 标签集合
     */
    private Set<Tag> tags;

    /**
     * 是否允许评论
     */
    private Boolean allowComment;

    /**
     * 是否是私人文章
     */
    private Boolean isPrivate;

    /**
     * 特征图像
     */
    private String featureImage;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getFeatureImage() {
        return featureImage;
    }

    public void setFeatureImage(String featureImage) {
        this.featureImage = featureImage;
    }

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public ArticleStatus getStatus() {
        return status;
    }

    public void setStatus(ArticleStatus status) {
        this.status = status;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(LocalDateTime modifyAt) {
        this.modifyAt = modifyAt;
    }

    public LocalDateTime getPostAt() {
        return postAt;
    }

    public void setPostAt(LocalDateTime postAt) {
        this.postAt = postAt;
    }
}
