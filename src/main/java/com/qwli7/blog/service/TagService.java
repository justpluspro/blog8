package com.qwli7.blog.service;

import com.qwli7.blog.entity.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
public interface TagService {

    void save(Tag tag);

    List<Tag> findAllTags();

    void deleteTag(final int id);


    void updateTag(final Tag tag);
}
