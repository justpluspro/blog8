package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.MomentArchive;
import com.qwli7.blog.entity.vo.MomentQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * 2021/2/22 13:49
 * 功能：MomentMapper
 **/
@Mapper
public interface MomentMapper {

    /**
     * 插入动态
     * @param moment moment
     */
    void insert(Moment moment);

    /**
     * 更新动态
     * @param moment moment
     */
    void update(Moment moment);

    /**
     * 根据 id 查询动态
     * @param id id
     * @return Moment
     */
    Optional<Moment> findById(int id);

    /**
     * 删除动态
     * @param id id
     */
    void deleteById(int id);

    /**
     * 更新动态点击量
     * @param id id
     * @param hits hits
     */
    void updateHits(@Param("id") int id, @Param("hits") int hits);

    /**
     * 添加点击量
     * @param id id
     * @param hits hits
     */
    void addHits(@Param("id") int id , @Param("hits") int hits);

    /**
     * 更新评论数
     * @param id id
     * @param comments comments
     */
    void updateComments(@Param("id") int id, @Param("comments") int comments);

    /**
     * 添加评论数
     * @param id id
     * @param comments comments
     */
    void addComments(@Param("id") int id, @Param("comments") int comments);

    /**
     * 分页查询动态
     * @param queryParam queryParam
     * @return List
     */
    List<Moment> findPage(MomentQueryParam queryParam);

    /**
     * 获取动态的数量
     * @param queryParam queryParam
     * @return int
     */
    int count(MomentQueryParam queryParam);

    /**
     * 获取归档数量
     * @param queryParam queryParam
     * @return int
     */
    int countArchive(MomentQueryParam queryParam);

    /**
     * 查询归档列表
     * @param queryParam queryParam
     * @return List
     */
    List<MomentArchive> findArchivePage(MomentQueryParam queryParam);

    /**
     * 获取上一篇动态
     * @param moment moment
     * @return Optional
     */
    Optional<Moment> findPreMoment(Moment moment);

    /**
     * 获取下一篇动态
     * @param moment moment
     * @return Optional
     */
    Optional<Moment> findNextMoment(Moment moment);

    /**
     * 获取最近的动态
     * @param queryParam queryParam
     * @return MomentArchive
     */
    MomentArchive findLatestMoments(MomentQueryParam queryParam);
}
