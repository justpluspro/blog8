package com.qwli7.blog.service;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.dto.PageResult;

/**
 * @author qwli7
 * @date 2023/2/20 15:29
 * 功能：blog8
 **/
public interface TagService {


    void addTag(Tag tag);


    void deleteTag(Integer id);


    PageResult<Tag> queryTags();
}
