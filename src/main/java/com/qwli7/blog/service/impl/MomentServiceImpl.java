package com.qwli7.blog.service.impl;

import com.qwli7.blog.dao.MomentDao;
import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.dto.SavedMoment;
import com.qwli7.blog.entity.enums.MomentState;
import com.qwli7.blog.entity.vo.MomentBean;
import com.qwli7.blog.entity.vo.MomentQueryParams;
import com.qwli7.blog.service.MomentService;
import org.springframework.stereotype.Service;

/**
 * @author qwli7
 * @date 2023/2/17 16:43
 * 功能：blog8
 **/
@Service
public class MomentServiceImpl implements MomentService {

    private final MomentDao momentDao;

    public MomentServiceImpl(MomentDao momentDao) {
        this.momentDao = momentDao;
    }


    @Override
    public SavedMoment saveMoment(MomentBean momentBean) {
        Moment moment = new Moment();
        moment.setContent(momentBean.getContent());
        moment.setHits(0);
        moment.setComments(0);
        moment.setAllowComment(momentBean.getAllowComment());
        moment.setState(MomentState.POSTED);
        momentDao.save(moment);

        return new SavedMoment(moment);
    }

    @Override
    public PageResult<Moment> queryMoments(MomentQueryParams momentQueryParams) {
        return null;
    }
}
