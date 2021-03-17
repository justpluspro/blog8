package com.qwli7.blog.service;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.MomentQueryParam;

import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
public interface MomentService {
    int saveMoment(Moment moment);

    void update(Moment moment);

    void delete(int id);

    Moment getMomentForEdit(int id);

    void updateHits(int id, int hits);

    Optional<Moment> getMoment(int id);

    PageDto<Moment> selectPage(MomentQueryParam queryParam);
}

