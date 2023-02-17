package com.qwli7.blog.entity;

import com.qwli7.blog.entity.enums.ArticleState;
import com.qwli7.blog.entity.enums.MomentState;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author qwli7
 * @date 2023/2/17 16:40
 * 功能：blog8
 **/
@Entity(name = "blog_moment")
@Table
public class Moment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Lob
    @Column(name = "content", length = 2048, nullable = false)
    private String content;

    @Column(name = "state", nullable = false)
    private MomentState state;


    @Column(name = "create_time", nullable = false)
    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(name = "posted_time")
    private LocalDateTime postedTime;

    @Column(name = "modified_time", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedTime;


    @Column(name = "hits", nullable = false)
    private Integer hits;

    @Column(name = "comments", nullable = false)
    private Integer comments;


    @Column(name = "allow_comment", nullable = false)
    private Boolean allowComment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MomentState getState() {
        return state;
    }

    public void setState(MomentState state) {
        this.state = state;
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

    public Boolean getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(Boolean allowComment) {
        this.allowComment = allowComment;
    }
}
