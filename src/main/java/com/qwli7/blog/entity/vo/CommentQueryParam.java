package com.qwli7.blog.entity.vo;

import com.qwli7.blog.entity.CommentModule;

import java.io.Serializable;

public class CommentQueryParam extends AbstractQueryParam implements Serializable {

    private CommentModule commentModule;

    public CommentModule getCommentModule() {
        return commentModule;
    }

    public void setCommentModule(CommentModule commentModule) {
        this.commentModule = commentModule;
    }
}
