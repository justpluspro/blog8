package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:49
 * 功能：blog8
 **/
@Mapper
public interface CategoryMapper {

    Optional<Category> findByName(String name);

    void insert(Category category);

    List<Category> findAll();

    Optional<Category> findById(int id);

    void deleteById(int id);

    void update(Category category);
}
