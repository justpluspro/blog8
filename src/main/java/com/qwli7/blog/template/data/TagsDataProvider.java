package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.service.TagService;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 标签数据提供者
 * @author liqiwen
 * @since 2.5
 * @date 2021-03-17
 */
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
