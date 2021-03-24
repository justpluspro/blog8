package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.event.CategoryDeleteEvent;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.mapper.CategoryMapper;
import com.qwli7.blog.service.CategoryService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final ApplicationEventPublisher publisher;

    public CategoryServiceImpl(CategoryMapper categoryMapper, ApplicationEventPublisher publisher) {
        super();
        this.categoryMapper = categoryMapper;
        this.publisher = publisher;
    }


    @Transactional(readOnly = true)
    @Override
    public List<Category> findAllCategories() {
        return categoryMapper.findAll();
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Category> selectByName(String name) {
        return categoryMapper.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Category> selectById(int id) {
        return categoryMapper.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteCategory(int id) {
        final Optional<Category> categoryOp = categoryMapper.findById(id);
        if(!categoryOp.isPresent()) {
            return;
        }
        categoryMapper.deleteById(id);
        publisher.publishEvent(new CategoryDeleteEvent(this, categoryOp.get()));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int saveCategory(Category category) {
        if(categoryMapper.findByName(category.getName()).isPresent()) {
            throw new LogicException("category.exists", "分类存在");
        }
        category.setCreateAt(LocalDateTime.now());
        category.setModifyAt(LocalDateTime.now());
        categoryMapper.insert(category);

        return category.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(final Category category) {
        final Optional<Category> categoryOp = categoryMapper.findById(category.getId());
        if(!categoryOp.isPresent()) {
            throw new ResourceNotFoundException("category.notExists", "分类不存在");
        }
        final Category old = categoryOp.get();
        if(old.getName().equals(category.getName())) {
            return;
        }
        old.setName(category.getName());
        old.setModifyAt(LocalDateTime.now());
        categoryMapper.update(old);
    }
}
