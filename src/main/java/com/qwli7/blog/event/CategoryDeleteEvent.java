package com.qwli7.blog.event;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.entity.Tag;
import org.springframework.context.ApplicationEvent;

public class CategoryDeleteEvent extends ApplicationEvent {

    private final Category category;

    public Category getCategory() {
        return category;
    }

    public CategoryDeleteEvent(Object source, Category category) {
        super(source);
        this.category = category;
    }
}
