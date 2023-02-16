package com.qwli7.blog.service;

import com.qwli7.blog.dao.CategoryDao;
import com.qwli7.blog.entity.dto.CategoryDto;

import java.util.List;

/**
 * @author qwli7 
 * @date 2023/2/16 17:31
 * 功能：blog8
 **/
public interface CategoryService {


    List<CategoryDto> getAll();
}
