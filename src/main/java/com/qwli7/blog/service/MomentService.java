package com.qwli7.blog.service;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.MomentArchiveDetail;
import com.qwli7.blog.entity.dto.MomentNav;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.vo.MomentQueryParams;

import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/17 16:42
 * 功能：blog8
 **/
public interface MomentService {


    Optional<Moment> getLatestMoment();

    PageResult<Moment> findMoments(MomentQueryParams momentQueryParams);

    PageResult<MomentArchiveDetail> findArchiveMoments(MomentQueryParams momentQueryParams);

    MomentNav findMomentNav(Integer momentId);

    void addMoment(Moment moment);

    void deleteMoment(Integer id);

    void updateMoment(Integer id);
}
