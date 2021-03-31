package com.qwli7.blog.event;

import com.qwli7.blog.entity.Tag;
import org.springframework.context.ApplicationEvent;

/**
 * 标签删除事件
 * @author liqiwen
 * @since 1.2
 */
public class TagDeleteEvent extends ApplicationEvent {

    /**
     * 当前删除的标签
     */
    private final Tag tag;

    public Tag getTag() {
        return tag;
    }

    public TagDeleteEvent(Object source, Tag tag) {
        super(source);
        this.tag = tag;
    }
}
