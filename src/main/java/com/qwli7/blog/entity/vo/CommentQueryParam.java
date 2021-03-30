package com.qwli7.blog.entity.vo;

import com.qwli7.blog.entity.CommentModule;

import java.io.Serializable;

/**
 * 评论查询参数
 * @author liqiwen
 * @since 1.2
 */
public class CommentQueryParam extends AbstractQueryParam implements Serializable {

    /**
     * 评论模块
     */
    private CommentModule commentModule;

    public CommentModule getCommentModule() {
        return commentModule;
    }

    public void setCommentModule(CommentModule commentModule) {
        this.commentModule = commentModule;
    }
}
