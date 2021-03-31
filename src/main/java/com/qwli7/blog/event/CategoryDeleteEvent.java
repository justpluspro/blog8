package com.qwli7.blog.event;

import com.qwli7.blog.entity.Category;
import org.springframework.context.ApplicationEvent;

/**
 * 分类删除事件
 * @author liqiwen
 * @since 1.2
 */
public class CategoryDeleteEvent extends ApplicationEvent {

    /**
     * 当前删除的分类
     */
    private final Category category;

    public Category getCategory() {
        return category;
    }

    public CategoryDeleteEvent(Object source, Category category) {
        super(source);
        this.category = category;
    }
}
