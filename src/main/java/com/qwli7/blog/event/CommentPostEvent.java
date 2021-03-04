package com.qwli7.blog.event;

import com.qwli7.blog.entity.Comment;
import org.springframework.context.ApplicationEvent;

public class CommentPostEvent extends ApplicationEvent {

    private final Comment comment;


    public Comment getComment() {
        return comment;
    }

    public CommentPostEvent(Object source, Comment comment) {
        super(source);
        this.comment = comment;
    }
}
