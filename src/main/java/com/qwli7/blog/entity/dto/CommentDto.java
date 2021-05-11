package com.qwli7.blog.entity.dto;

import com.qwli7.blog.entity.Comment;

import java.io.Serializable;

/**
 * 评论 DTO
 * @author liqiwen
 * @since 2.2
 */
public class CommentDto implements Serializable {



    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.name = comment.getName();
        final Comment parent = comment.getParent();
        if(parent != null) {
            this.parent = new CommentDto(parent);
        }

    }

    private Integer id;

    private String name;

    private String content;

    private CommentDto parent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentDto getParent() {
        return parent;
    }

    public void setParent(CommentDto parent) {
        this.parent = parent;
    }
}
