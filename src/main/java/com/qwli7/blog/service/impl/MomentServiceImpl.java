package com.qwli7.blog.service.impl;

import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.entity.CommentModule;
import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.MomentQueryParam;
import com.qwli7.blog.event.MomentDeleteEvent;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.mapper.CommentMapper;
import com.qwli7.blog.mapper.MomentMapper;
import com.qwli7.blog.service.CommentModuleHandler;
import com.qwli7.blog.service.Markdown2Html;
import com.qwli7.blog.service.MomentService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
@Service
public class MomentServiceImpl implements MomentService, CommentModuleHandler {

    private final static String MODULE_NAME = "moment";

    private final MomentMapper momentMapper;
    private final ApplicationEventPublisher publisher;
    private final Markdown2Html markdown2Html;
    private final CommentMapper commentMapper;

    public MomentServiceImpl(MomentMapper momentMapper, Markdown2Html markdown2Html,
                             CommentMapper commentMapper,
                             ApplicationEventPublisher applicationEventPublisher) {
        this.momentMapper = momentMapper;
        this.markdown2Html = markdown2Html;
        this.commentMapper = commentMapper;
        this.publisher = applicationEventPublisher;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int saveMoment(Moment moment) {
        moment.setHits(0);
        moment.setComments(0);
        moment.setCreateAt(LocalDateTime.now());
        moment.setModifyAt(LocalDateTime.now());
        momentMapper.insert(moment);
        return moment.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(Moment moment) {
        momentMapper.selectById(moment.getId()).orElseThrow(()
                -> new ResourceNotFoundException("moment.notExists", "动态不存在"));
        moment.setModifyAt(LocalDateTime.now());
        momentMapper.update(moment);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(int id) {
        final Moment oldMoment = momentMapper.selectById(id).orElseThrow(()
                -> new ResourceNotFoundException("moment.notExists", "动态不存在"));
        momentMapper.deleteById(id);
        commentMapper.deleteByModule(new CommentModule(id, getModuleName()));
        publisher.publishEvent(new MomentDeleteEvent(this, oldMoment));
    }

    @Override
    public Moment getMomentForEdit(int id) {
        return momentMapper.selectById(id).orElseThrow(()
                -> new ResourceNotFoundException("moment.notExists", "动态不存在"));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Moment> getMoment(int id) {
        final Optional<Moment> momentOp= momentMapper.selectById(id);
        if(momentOp.isPresent()) {
            Moment moment = momentOp.get();
//           markdown2Html.
            processMoment(moment);
            return Optional.of(moment);
        }
        return momentOp;
    }

    @Override
    public PageDto<Moment> selectPage(MomentQueryParam queryParam) {
        int count = momentMapper.count(queryParam);
        if(count == 0) {
            return new PageDto<>(queryParam, 0, new ArrayList<>());
        }
        List<Moment> moments = momentMapper.selectPage(queryParam);
        if(CollectionUtils.isEmpty(moments)) {
            return new PageDto<>(queryParam, 0, new ArrayList<>());
        }
        PageDto<Moment> pageDto = new PageDto<>(queryParam, count, moments);
        return pageDto;
    }

    private void processMoment(Moment moment) {
        processMoments(Collections.singletonList(moment));
    }

    private void processMoments(List<Moment> moments) {

        Map<Integer, String> contentMap = moments.stream().filter(m -> m.getContent() != null)
                .collect(Collectors.toMap(Moment::getId, Moment::getContent));
        if(contentMap.isEmpty()) {
            return;
        }
        Map<Integer, String> markdownMap = markdown2Html.toHtmls(contentMap);
        moments.forEach(e -> {
            e.setContent(markdownMap.get(e.getId()));
        });
    }

    @Override
    public String getModuleName() {
        return Moment.class.getName();
    }
}
