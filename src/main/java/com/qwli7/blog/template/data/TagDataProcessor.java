package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagDataProcessor extends DataProcessor<Tag> {

    public TagDataProcessor() {
        super("tag");
    }
}
