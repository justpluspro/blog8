package com.qwli7.blog.service;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.MomentArchive;
import com.qwli7.blog.entity.MomentNav;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.MomentQueryParam;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * 2021/2/22 13:09
 * 功能：MomentService
 **/
public interface MomentService {

    /**
     * 保存动态
     * @param moment moment
     * @return int
     */
    int saveMoment(Moment moment);

    /**
     * 更新动态
     * @param moment moment
     */
    void update(Moment moment);

    /**
     * 删除动态
     * @param id id
     */
    void delete(int id);

    /**
     * 获取动态用以更新
     * @param id id
     * @return Moment
     */
    Optional<Moment> getMomentForEdit(int id);

    /**
     * 更新点击量
     * @param id id
     * @param hits hits
     */
    void updateHits(int id, int hits);

    /**
     * 获取动态
     * @param id id
     * @return Moment
     */
    Optional<Moment> getMoment(int id);

    /**
     * 分页查询动态
     * @param queryParam queryParam
     * @return PageDto
     */
    PageDto<Moment> selectPage(MomentQueryParam queryParam);

    /**
     * 获取归档动态列表
     * @return list
     */
    PageDto<MomentArchive> selectArchivePage(MomentQueryParam queryParam);

    /**
     * 获取动态导航
     * @param id id
     * @return MomentNav
     */
    Optional<MomentNav> selectMomentNav(int id);

    /**
     * 点击动态
     * @param id id
     */
    void hits(int id);

    /**
     * 获取最近的动态
     * @return MomentArchive
     */
    MomentArchive selectLatestMoments();

    /**
     * 根据 id 查找动态
     * @param id id
     * @return Moment
     */
    Moment selectById(int id);
}

