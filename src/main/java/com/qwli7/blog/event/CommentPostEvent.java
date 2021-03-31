package com.qwli7.blog.event;

import com.qwli7.blog.entity.Comment;
import org.springframework.context.ApplicationEvent;

/**
 * 评论发布事件
 * @author liqiwen
 * @since 1.2
 */
public class CommentPostEvent extends ApplicationEvent {

    /**
     * 当前已发布的评论
     */
    private final Comment comment;

    public Comment getComment() {
        return comment;
    }

    public CommentPostEvent(Object source, Comment comment) {
        super(source);
        this.comment = comment;
    }
}
