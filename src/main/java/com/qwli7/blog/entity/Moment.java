package com.qwli7.blog.entity;

import com.qwli7.blog.entity.enums.MomentStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author qwli7
 * @date 2023/2/17 16:40
 * 功能：blog8
 **/
public class Moment implements Serializable {

    private Integer id;

    private String content;

    private MomentStatus status;

    private LocalDateTime createTime;

    private LocalDateTime postedTime;

    private LocalDateTime modifiedTime;

    private Integer hits;

    private Integer comments;

    private Boolean allowComment;

    //私人动态
    private Boolean privateMoment;

    //特征图像，使用内容中的第一张图片作为特征图像
    private String featureImage;

    public String getFeatureImage() {
        return featureImage;
    }

    public void setFeatureImage(String featureImage) {
        this.featureImage = featureImage;
    }

    public Boolean getPrivateMoment() {
        return privateMoment;
    }

    public void setPrivateMoment(Boolean privateMoment) {
        this.privateMoment = privateMoment;
    }

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

    public MomentStatus getStatus() {
        return status;
    }

    public void setStatus(MomentStatus status) {
        this.status = status;
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
