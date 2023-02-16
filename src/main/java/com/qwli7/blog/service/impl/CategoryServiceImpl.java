package com.qwli7.blog.service.impl;

import com.qwli7.blog.dao.CategoryDao;
import com.qwli7.blog.entity.Category;
import com.qwli7.blog.entity.dto.CategoryDto;
import com.qwli7.blog.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author qwli7 
 * @date 2023/2/16 17:32
 * 功能：blog8
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<CategoryDto> getAll() {
        Iterator<Category> categoryIterator = categoryDao.findAll().iterator();
        List<CategoryDto> categories = new ArrayList<>();
        while (categoryIterator.hasNext()) {
            Category category = categoryIterator.next();
            CategoryDto categoryDto = new CategoryDto(category);
            categories.add(categoryDto);
        }
        return categories;
    }
}
