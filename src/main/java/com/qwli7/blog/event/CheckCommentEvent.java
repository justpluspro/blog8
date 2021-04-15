package com.qwli7.blog.event;

import com.qwli7.blog.entity.Comment;
import org.springframework.context.ApplicationEvent;


/**
 * 评论审核通过事件
 * @author liqiwen
 * @since 2.3
 */
public class CheckCommentEvent extends ApplicationEvent {

    /**
     * 审核通过的事件
     */
    private final Comment comment;

    public CheckCommentEvent(Object source, Comment comment) {
        super(source);
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }
}
