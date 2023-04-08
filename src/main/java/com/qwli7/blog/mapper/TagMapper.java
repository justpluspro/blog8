package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * @author qwli7
 * @date 2023/2/20 15:30
 * 功能：blog8
 **/
@Mapper
public interface TagMapper {


    void insert(Tag tag);

    Optional<Tag> findByName(@Param("tagName") String tagName);
    Optional<Tag> findById(@Param("id") Integer id);

    void delete(@Param("id") Integer id);
}
