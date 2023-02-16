package com.qwli7.blog.dao;

import com.qwli7.blog.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author qwli7 
 * @date 2023/2/16 17:32
 * 功能：blog8
 **/
@Repository
public interface CategoryDao extends CrudRepository<Category, Integer> {
}
