package com.qwli7.blog.service;

import com.qwli7.blog.entity.Moment;

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
}
