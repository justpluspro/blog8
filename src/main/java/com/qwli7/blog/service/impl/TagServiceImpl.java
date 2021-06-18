package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.CommonQueryParam;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：标签业务实现类
 **/
@Service
public class TagServiceImpl implements TagService {

    /**
     * 标签 Mapper
     */
    private final TagMapper tagMapper;

    /**
     * 文章标签 Mapper
     */
    private final ArticleTagMapper articleTagMapper;

    /**
     * 事件发布
     */
    private final ApplicationEventPublisher publisher;

    public TagServiceImpl(TagMapper tagMapper, ArticleTagMapper articleTagMapper, ApplicationEventPublisher publisher) {
        this.tagMapper = tagMapper;
        this.articleTagMapper = articleTagMapper;
        this.publisher = publisher;
    }

    /**
     * 新增标签
     * @param tag tag
     */
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

    /**
     * 分页查询标签
     * @param queryParam queryParam
     * @return PageDto
     */
    @Transactional(readOnly = true)
    @Override
    public PageDto<Tag> findPage(CommonQueryParam queryParam) {
        int count = tagMapper.count(queryParam);
        if(count == 0) {
            return new PageDto<>(queryParam, 0, new ArrayList<>());
        }
        return new PageDto<>(queryParam, count, tagMapper.findPage(queryParam));
    }

    /**
     * 根据 id 查询标签
     * @param id id
     * @return Tag
     */
    @Transactional(readOnly = true)
    @Override
    public Optional<Tag> findById(int id) {
        return tagMapper.findById(id);
    }

    /**
     * 删除标签
     * @param id id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LogicException.class)
    @Override
    public void deleteTag(int id) {
        Tag tag = tagMapper.findById(id).orElseThrow(()
                -> new LogicException("tag.notExists", "标签不存在"));
        tagMapper.deleteById(id);
        articleTagMapper.deleteByTag(tag);
        publisher.publishEvent(new TagDeleteEvent(this, tag));
    }

    /**
     * 更新标签
     * @param tag tag
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LogicException.class)
    @Override
    public void updateTag(Tag tag) {
        final Optional<Tag> tagOp = tagMapper.findById(tag.getId());
        if(!tagOp.isPresent()) {
            throw new LogicException("tag.notExists", "标签不存在");
        }
        final Tag old = tagOp.get();
        if(old.getName().equals(tag.getName())) {
            return;
        }
        tag.setModifyAt(LocalDateTime.now());
        tagMapper.update(tag);
    }
}
