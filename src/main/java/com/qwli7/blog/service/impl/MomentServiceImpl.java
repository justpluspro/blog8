package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.mapper.MomentMapper;
import com.qwli7.blog.service.MomentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
@Service
public class MomentServiceImpl implements MomentService {

    private final MomentMapper momentMapper;

    public MomentServiceImpl(MomentMapper momentMapper) {
        this.momentMapper = momentMapper;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LogicException.class)
    @Override
    public int saveMoment(Moment moment) {
        return 0;
    }

    @Override
    public void update(Moment moment) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Moment getMomentForEdit(int id) {
        return null;
    }
}
