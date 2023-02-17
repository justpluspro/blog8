package com.qwli7.blog.entity;

import com.qwli7.blog.entity.enums.ArticleState;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author qwli7 
 * @date 2023/2/16 14:58
 * 功能：blog8
 **/
@Entity(name = "blog_article")
@Table
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @Column(name = "title", length = 128, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", length = 10240, nullable = false)
    private String content;

    @Column(name = "digest", length = 1024)
    private String digest;

    @Column(name = "feature_image", length = 128)
    private String featureImage;

    @Column(name = "alias", length = 32)
    private String alias;

    @Column(name = "state", nullable = false)
    private ArticleState state;

    @Column(name = "hits", nullable = false)
    private Integer hits;

    @Column(name = "comments", nullable = false)
    private Integer comments;

    @Column(name = "create_time", nullable = false)
    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(name = "posted_time")
    private LocalDateTime postedTime;

    @Column(name = "modified_time", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedTime;

    @OneToOne
    @JoinColumn(name = "fk_category_id", referencedColumnName = "id")
    private Category category;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ArticleState getState() {
        return state;
    }

    public void setState(ArticleState state) {
        this.state = state;
    }

    public LocalDateTime getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(LocalDateTime postedTime) {
        this.postedTime = postedTime;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
