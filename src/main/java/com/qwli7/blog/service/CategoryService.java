package com.qwli7.blog.service;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.entity.dto.CategoryDto;
import com.qwli7.blog.entity.vo.CategoryQueryParams;

import java.util.List;

/**
 * @author qwli7 
 * @date 2023/2/16 17:31
 * 功能：blog8
 **/
public interface CategoryService {

    List<Category> queryCategory(CategoryQueryParams categoryQueryParams);

    void addCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Integer id);

    Category getCategory(Integer id);

}
