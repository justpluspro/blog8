package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.exception.BizException;
import com.qwli7.blog.exception.Message;
import com.qwli7.blog.mapper.ArticleTagMapper;
import com.qwli7.blog.mapper.TagMapper;
import com.qwli7.blog.service.TagService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/20 15:29
 * 功能：blog8
 **/
@Service
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    private final ArticleTagMapper articleTagMapper;

    public TagServiceImpl(TagMapper tagMapper, ArticleTagMapper articleTagMapper) {
        this.tagMapper = tagMapper;
        this.articleTagMapper = articleTagMapper;
    }


    @Override
    public void addTag(Tag tag) {
        String tagName = tag.getTagName();
        Optional<Tag> tagOptional = tagMapper.findByName(tagName);
        if(tagOptional.isPresent()) {
            throw new BizException(Message.TAG_EXISTS);
        }
        tagMapper.insert(tag);
    }

    @Override
    public void deleteTag(Integer id) {
        Tag tag = tagMapper.findById(id).orElseThrow(() -> new BizException(Message.TAG_NOT_FOUND));
        tagMapper.delete(tag.getId());
        articleTagMapper.deleteByTag(tag);
    }

    @Override
    public PageResult<Tag> queryTags() {
        return null;
    }
}
