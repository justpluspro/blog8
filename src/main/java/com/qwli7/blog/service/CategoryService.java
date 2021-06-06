package com.qwli7.blog.service;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.exception.LogicException;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * 2021/2/22 13:09
 * 功能：CategoryService
 **/
public interface CategoryService {

    /**
     * 保存分类
     * @param category category
     * @throws LogicException LogicException
     */
    void save(Category category) throws LogicException;

    /**
     * 查询分类
     * @param id id
     * @return Category
     * @throws LogicException LogicException
     */
    Optional<Category> findById(int id) throws LogicException;

    /**
     * 获取所有的分类
     * @return list
     */
    List<Category> findAll();

    /**
     * 根据分类查询分类
     * @param name name
     * @return Category
     * @throws LogicException LogicException
     */
    Optional<Category> findByName(String name) throws LogicException;

    /**
     * 删除分类
     * @param id id
     * @throws LogicException LogicException
     */
    void delete(final int id) throws LogicException;

    /**
     * 更新标签
     * @param category category
     * @throws LogicException LogicException
     */
    void update(final Category category) throws LogicException;
}
