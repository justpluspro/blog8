package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.CommonQueryParam;
import com.qwli7.blog.entity.vo.TagQueryParam;
import com.qwli7.blog.event.TagDeleteEvent;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.mapper.ArticleTagMapper;
import com.qwli7.blog.mapper.TagMapper;
import com.qwli7.blog.service.TagService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
@Service
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    private final ArticleTagMapper articleTagMapper;

    private final ApplicationEventPublisher publisher;

    public TagServiceImpl(TagMapper tagMapper, ArticleTagMapper articleTagMapper, ApplicationEventPublisher publisher) {
        this.tagMapper = tagMapper;
        this.articleTagMapper = articleTagMapper;
        this.publisher = publisher;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LogicException.class)
    @Override
    public void save(Tag tag) {
        final Optional<Tag> tagOp = tagMapper.findByName(tag.getName());
        if(tagOp.isPresent()) {
            throw new LogicException("tag.exists", "标签存在");
        }
        tag.setModifyAt(LocalDateTime.now());
        tag.setCreateAt(LocalDateTime.now());
        tagMapper.insert(tag);
    }

    @Transactional(readOnly = true)
    @Override
    public PageDto<Tag> selectPage(CommonQueryParam queryParam) {
        int count = tagMapper.count(queryParam);
        if(count == 0) {
            return new PageDto<>(queryParam, 0, new ArrayList<>());
        }
        return new PageDto<>(queryParam, count, tagMapper.selectPage(queryParam));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Tag> selectById(int id) {
        return tagMapper.selectById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteTag(int id) {
        final Optional<Tag> tagOp = tagMapper.findById(id);
        if(!tagOp.isPresent()) {
            return;
        }
        tagMapper.deleteById(id);
        articleTagMapper.deleteByTag(tagOp.get());
        publisher.publishEvent(new TagDeleteEvent(this, tagOp.get()));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateTag(Tag tag) {
        final Optional<Tag> tagOp = tagMapper.findById(tag.getId());
        if(!tagOp.isPresent()) {
            throw new ResourceNotFoundException("tag.notExists", "标签不存在");
        }
        final Tag old = tagOp.get();
        if(old.getName().equals(tag.getName())) {
            return;
        }
        tag.setName(tag.getName());
        tag.setModifyAt(LocalDateTime.now());
        tagMapper.update(tag);
    }
}
