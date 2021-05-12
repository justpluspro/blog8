package com.qwli7.blog.entity.dto;

import com.qwli7.blog.entity.Comment;
import com.qwli7.blog.entity.CommentModule;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 评论 DTO
 * @author liqiwen
 * @since 2.2
 */
public class CommentDto implements Serializable {

    public CommentDto() {
        super();
    }



    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.name = comment.getName();
        this.createAt = comment.getCreateAt();
        this.admin = comment.getAdmin();
        this.commentModule = comment.getModule();

        final Comment parent = comment.getParent();
        if(parent != null) {
            CommentDto commentDto = new CommentDto();
            commentDto.setAdmin(parent.getAdmin());
            commentDto.setId(parent.getId());
            commentDto.setName(parent.getName());
            commentDto.setCommentModule(commentModule);
            this.parent = commentDto;
        }

    }

    private Integer id;

    private String name;

    private String content;

    private LocalDateTime createAt;

    private boolean admin;

    private CommentDto parent;

    private CommentModule commentModule;

    public CommentModule getCommentModule() {
        return commentModule;
    }

    public void setCommentModule(CommentModule commentModule) {
        this.commentModule = commentModule;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

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
