package com.qwli7.blog.dao;

import com.qwli7.blog.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author qwli7
 * @date 2023/2/20 15:30
 * 功能：blog8
 **/
@Repository
public interface TagDao extends CrudRepository<Tag, Integer> {
}
