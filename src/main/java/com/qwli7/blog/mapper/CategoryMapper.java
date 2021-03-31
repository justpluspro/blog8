package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * 2021/2/22 13:49
 * 功能：CategoryMapper
 **/
@Mapper
public interface CategoryMapper {

    /**
     * 获取分类
     * @param name name
     * @return Category
     */
    Optional<Category> findByName(String name);

    /**
     * 插入分类
     * @param category category
     */
    void insert(Category category);

    /**
     * 获取所有的分类
     * @return List
     */
    List<Category> findAll();

    /**
     * 根据 id 查询分类
     * @param id id
     * @return Category
     */
    Optional<Category> findById(int id);

    /**
     * 删除分类
     * @param id id
     */
    void deleteById(int id);

    /**
     * 更新分类
     * @param category category
     */
    void update(Category category);
}
