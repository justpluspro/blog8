package com.qwli7.blog.service;

import com.qwli7.blog.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
public interface CategoryService {

    int saveCategory(Category category);


    List<Category> findAllCategories();

    Optional<Category> getCategory(String name);

    void deleteCategory(final int id);

    void updateCategory(final Category category);
}
