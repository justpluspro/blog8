package com.qwli7.blog.service.impl;

import com.qwli7.blog.dao.TagDao;
import com.qwli7.blog.service.TagService;
import org.springframework.stereotype.Service;

/**
 * @author qwli7
 * @date 2023/2/20 15:29
 * 功能：blog8
 **/
@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }


}
