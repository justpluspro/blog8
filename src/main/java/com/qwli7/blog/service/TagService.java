package com.qwli7.blog.service;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.CommonQueryParam;
import com.qwli7.blog.entity.vo.TagQueryParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author qwli7
 * @date 2021/2/22 13:09
 * 功能：blog8
 **/
public interface TagService {

    void save(Tag tag);

    PageDto<Tag> selectPage(CommonQueryParam queryParam);

    void deleteTag(final int id);


    void updateTag(final Tag tag);

    Optional<Tag> selectById(int id);
}
