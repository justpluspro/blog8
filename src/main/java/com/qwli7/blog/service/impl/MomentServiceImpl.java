package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.MomentArchiveDetail;
import com.qwli7.blog.entity.dto.MomentNav;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.enums.MomentStatus;
import com.qwli7.blog.entity.vo.MomentNavQueryParam;
import com.qwli7.blog.entity.vo.MomentQueryParams;
import com.qwli7.blog.exception.BizException;
import com.qwli7.blog.exception.Message;
import com.qwli7.blog.mapper.MomentMapper;
import com.qwli7.blog.plugin.html.JsoupUtil;
import com.qwli7.blog.plugin.md.MarkdownHandler;
import com.qwli7.blog.service.MomentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/17 16:43
 * 功能：blog8
 **/
@Service
public class MomentServiceImpl implements MomentService {

    private final MomentMapper momentMapper;

    private final MarkdownHandler markdownHandler;

    public MomentServiceImpl(MomentMapper momentMapper, MarkdownHandler markdownHandler) {
        this.momentMapper = momentMapper;
        this.markdownHandler = markdownHandler;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void addMoment(Moment moment) {
        MomentStatus status = moment.getStatus();
        if (status == null) {
            status = MomentStatus.POSTED;
            moment.setStatus(status);
        }
        if (MomentStatus.POSTED.equals(status)) {
            moment.setPostedTime(LocalDateTime.now());
        }
        Boolean privateMoment = moment.getPrivateMoment();
        if (privateMoment == null) {
            moment.setPrivateMoment(false);
        }
        Boolean allowComment = moment.getAllowComment();
        if (allowComment == null) {
            moment.setAllowComment(true);
        }


        moment.setHits(0);
        moment.setComments(0);
        momentMapper.insert(moment);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteMoment(Integer id) {
        Moment moment = momentMapper.findById(id).orElseThrow(() -> new BizException(Message.MOMENT_NOT_EXISTS));
        momentMapper.deleteById(moment.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void updateMoment(Integer id) {
        Moment moment = momentMapper.findById(id).orElseThrow(() -> new BizException(Message.MOMENT_NOT_EXISTS));

    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Moment> getLatestMoment() {

        MomentQueryParams momentQueryParams = new MomentQueryParams();

        //如果已经登录，可以忽略该条件
        momentQueryParams.setStatus(MomentStatus.POSTED);
        momentQueryParams.setQueryPrivate(false);

        Optional<Moment> momentOptional = momentMapper.findLatestMoment(momentQueryParams);
        if (momentOptional.isPresent()) {
            Moment moment = momentOptional.get();
            String content = moment.getContent();
            String htmlContent = markdownHandler.toHtml(content);
            moment.setContent(htmlContent);
            return Optional.of(moment);
        }
        return Optional.empty();
    }

    @Override
    public PageResult<Moment> findMoments(MomentQueryParams momentQueryParams) {
        Integer count = momentMapper.count(momentQueryParams);

        return null;
    }




    @Transactional(readOnly = true)
    @Override
    public PageResult<MomentArchiveDetail> findArchiveMoments(MomentQueryParams momentQueryParams) {
        Integer count = momentMapper.countByDate(momentQueryParams);
        if (count == null) {
            return new PageResult<>(momentQueryParams, 0, new ArrayList<>());
        }
        List<MomentArchiveDetail> momentArchiveDetails = momentMapper.findArchiveMoments(momentQueryParams);
        if (CollectionUtils.isEmpty(momentArchiveDetails)) {
            return new PageResult<>(momentQueryParams, 0, new ArrayList<>());
        }
        //遍历内容，对内容部分进行解析
        for (MomentArchiveDetail momentArchiveDetail : momentArchiveDetails) {
            List<Moment> moments = momentArchiveDetail.getMoments();
            for (Moment moment : moments) {
                String content = moment.getContent();
                String htmlContent = markdownHandler.toHtml(content);
                moment.setContent(htmlContent);
                //获取第一张图，作为首图
                Optional<String> firstImageOp = JsoupUtil.getFirstImage(htmlContent);
                firstImageOp.ifPresent(moment::setFeatureImage);
            }
        }
        return new PageResult<>(momentQueryParams, count, momentArchiveDetails);
    }

    @Override
    public MomentNav findMomentNav(Integer momentId) {
        Optional<Moment> momentOp = momentMapper.findById(momentId);
        if(!momentOp.isPresent()) {
            return new MomentNav();
        }
        MomentNavQueryParam momentNavQueryParam = new MomentNavQueryParam();
        Moment currentMoment = momentOp.get();
        momentNavQueryParam.setCurrentMoment(currentMoment);
        momentNavQueryParam.setQueryPrivate(true);
        MomentNav momentNav = new MomentNav();

        String content = currentMoment.getContent();
        String htmlContent = markdownHandler.toHtml(content);
        currentMoment.setContent(htmlContent);

        momentNav.setCurrentMoment(currentMoment);
        Optional<Moment> momentOptional = momentMapper.findPreMoment(momentNavQueryParam);
        momentOptional.ifPresent(momentNav::setPreMoment);
        momentOptional = momentMapper.findNextMoment(momentNavQueryParam);
        momentOptional.ifPresent(momentNav::setNextMoment);
        return momentNav;
    }
}
