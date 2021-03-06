package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.vo.CommonQueryParam;
import com.qwli7.blog.entity.vo.TagQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;


/**
 * @author qwli7
 * @date 2021/2/22 13:49
 * 功能：blog8
 **/
@Mapper
public interface TagMapper {

    Optional<Tag> findById(int id);

    List<Tag> findAll();

    Optional<Tag> findByName(String name);

    void deleteById(int id);

    void insert(Tag tag);

    void update(Tag tag);

    int count(CommonQueryParam queryParam);

    List<Tag> selectPage(CommonQueryParam queryParam);
}
