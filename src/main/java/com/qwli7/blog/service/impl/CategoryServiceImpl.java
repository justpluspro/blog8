package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.entity.vo.CategoryQueryParams;
import com.qwli7.blog.exception.BizException;
import com.qwli7.blog.exception.Message;
import com.qwli7.blog.mapper.CategoryMapper;
import com.qwli7.blog.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/16 17:32
 * 功能：blog8
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }


    @Override
    public List<Category> queryCategory(CategoryQueryParams categoryQueryParams) {
        return categoryMapper.findByParams(categoryQueryParams);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void addCategory(Category category) {
        String alias = category.getCategoryAlias();
        Optional<Category> categoryOp = categoryMapper.findByAlias(alias);
        if (categoryOp.isPresent()) {
            throw new BizException(Message.CATEGORY_ALIAS_EXISTS);
        }
        category.setCreateTime(LocalDateTime.now());
        category.setModifiedTime(LocalDateTime.now());
        categoryMapper.insert(category);
    }

    @Override
    public void updateCategory(Category category) {

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteCategory(Integer id) {
        Category category = categoryMapper.findById(id).orElseThrow(()
                -> new BizException(Message.CATEGORY_NOT_EXISTS));


    }

    @Override
    public Category getCategory(Integer id) {
        return categoryMapper.findById(id).orElseThrow(() -> new BizException(Message.CATEGORY_NOT_EXISTS));
    }

}
