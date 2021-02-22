package com.qwli7.blog.event;

import com.qwli7.blog.entity.Tag;
import org.springframework.context.ApplicationEvent;

public class TagDeleteEvent extends ApplicationEvent {

    private final Tag tag;

    public Tag getTag() {
        return tag;
    }

    public TagDeleteEvent(Object source, Tag tag) {
        super(source);
        this.tag = tag;
    }
}
