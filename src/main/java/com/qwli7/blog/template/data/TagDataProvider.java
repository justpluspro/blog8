package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.service.TagService;

import org.springframework.stereotype.Component;

@Component
public class TagDataProvider extends DataProvider<Tag> {

    private final TagService tagService;


    public TagDataProvider(TagService tagService) {
        super("tag");
        this.tagService = tagService;
    }
}
