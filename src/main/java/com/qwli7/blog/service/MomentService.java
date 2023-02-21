package com.qwli7.blog.service;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.dto.SavedMoment;
import com.qwli7.blog.entity.vo.MomentBean;
import com.qwli7.blog.entity.vo.MomentQueryParams;

import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/17 16:42
 * 功能：blog8
 **/
public interface MomentService {


    SavedMoment saveMoment(MomentBean momentBean);


    Optional<Moment> getLatestMoment();

    PageResult<Moment> queryMoments(MomentQueryParams momentQueryParams);
}
