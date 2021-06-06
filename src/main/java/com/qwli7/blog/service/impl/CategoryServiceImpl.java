package com.qwli7.blog.service.impl;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.event.CategoryDeleteEvent;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.mapper.ArticleMapper;
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
 * 功能：分类业务实现类
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final ArticleMapper articleMapper;

    private final ApplicationEventPublisher publisher;

    public CategoryServiceImpl(CategoryMapper categoryMapper, ArticleMapper articleMapper,
                               ApplicationEventPublisher publisher) {
        super();
        this.categoryMapper = categoryMapper;
        this.publisher = publisher;
        this.articleMapper = articleMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Category> findByName(String name) {
        return categoryMapper.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Category> findById(int id) {
        return categoryMapper.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delete(int id) {
        final Category category = categoryMapper.findById(id).orElseThrow(() ->
                new LogicException("category.notExists", "分类不存在"));

        long count = articleMapper.countByCategory(category);
        if(count > 0) {
            throw new LogicException("category.existsArticles", "分类下存在文章，无法删除");
        }
        categoryMapper.deleteById(id);
        publisher.publishEvent(new CategoryDeleteEvent(this, category));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(Category category) {
        final Optional<Category> categoryOp = categoryMapper.findByName(category.getName());
        if(categoryOp.isPresent()) {
            throw new LogicException("category.exists", "分类存在");
        }
        category.setCreateAt(LocalDateTime.now());
        category.setModifyAt(LocalDateTime.now());
        categoryMapper.insert(category);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(final Category category) {
        final Optional<Category> categoryOp = categoryMapper.findById(category.getId());
        if(!categoryOp.isPresent()) {
            throw new LogicException("category.notExists", "分类不存在");
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
