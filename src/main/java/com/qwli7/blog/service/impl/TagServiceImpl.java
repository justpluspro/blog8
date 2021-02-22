package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.event.TagDeleteEvent;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.mapper.TagMapper;
import com.qwli7.blog.service.TagService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    private final ApplicationEventPublisher publisher;

    public TagServiceImpl(TagMapper tagMapper, ApplicationEventPublisher publisher) {
        this.tagMapper = tagMapper;
        this.publisher = publisher;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Tag> findAllTags() {
        return tagMapper.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteTag(int id) {
        final Optional<Tag> tagOp = tagMapper.findById(id);
        if(!tagOp.isPresent()) {
            return;
        }
        tagMapper.deleteById(id);
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
        old.setName(tag.getName());
        old.setModifyAt(LocalDateTime.now());
        tagMapper.update(tag);
    }
}
