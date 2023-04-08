package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.entity.vo.CategoryQueryParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/16 17:32
 * 功能：blog8
 **/
@Mapper
public interface CategoryMapper {
    Optional<Category> findByAlias(@Param("alias") String alias);

    void insert(Category category);

    Optional<Category> findById(@Param("categoryId") Integer categoryId);

    List<Category> findByParams(CategoryQueryParams categoryQueryParams);
}
