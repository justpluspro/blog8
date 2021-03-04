package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.service.TagService;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TagsDataProvider extends AbstractDataProvider<PageDto<Tag>> {

    private final TagService tagService;


    public TagsDataProvider(TagService tagService) {
        super("tag");
        this.tagService = tagService;
    }


    @Override
    public PageDto<Tag> queryData(Map<String, String> attributeMap) {
        return null;
    }
}
