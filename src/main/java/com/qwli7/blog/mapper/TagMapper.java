package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.vo.CommonQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;


/**
 * @author qwli7
 * 2021/2/22 13:49
 * 功能：TagMapper
 **/
@Mapper
public interface TagMapper {

    /**
     * 根据 id 查找 Tag
     * @param id id
     * @return Tag
     */
    Optional<Tag> findById(int id);

    /**
     * 获取所有的 Tag
     * @return List
     */
    List<Tag> findAll();

    /**
     * 根据名称查询 Tag
     * @param name name
     * @return Tag
     */
    Optional<Tag> findByName(String name);

    /**
     * 删除 Tag
     * @param id id
     */
    void deleteById(int id);

    /**
     * 插入 Tag
     * @param tag tag
     */
    void insert(Tag tag);

    /**
     * 更新 Tag
     * @param tag tag
     */
    void update(Tag tag);

    /**
     * 统计 Tag 的数量
     * @param queryParam queryParam
     * @return int
     */
    int count(CommonQueryParam queryParam);

    /**
     * 分页查询标签
     * @param queryParam queryParam
     * @return List
     */
    List<Tag> selectPage(CommonQueryParam queryParam);

    /**
     * 根据 id 查询 Tag
     * @param id id
     * @return Tag
     */
    Optional<Tag> selectById(int id);
}
