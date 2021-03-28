package com.qwli7.blog.service;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.exception.LogicException;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
public interface CategoryService {

    void save(Category category) throws LogicException;

    Optional<Category> selectById(int id) throws LogicException;

    List<Category> findAllCategories();

    Optional<Category> selectByName(String name) throws LogicException;

    void delete(final int id) throws LogicException;

    void update(final Category category) throws LogicException;
}
