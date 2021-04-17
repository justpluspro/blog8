package com.qwli7.blog.service;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.CommonQueryParam;

import java.util.Optional;

/**
 * @author qwli7
 * 2021/2/22 13:09
 * 功能：TagService
 **/
public interface TagService {
    /**
     * 保存 tag
     * @param tag tag
     */
    void save(Tag tag);

    /**
     * 分页查询标签
     * @param queryParam queryParam
     * @return PageDto
     */
    PageDto<Tag> selectPage(CommonQueryParam queryParam);

    /**
     * 删除标签
     * @param id id
     */
    void deleteTag(final int id);

    /**
     * 更新标签
     * @param tag tag
     */
    void updateTag(final Tag tag);

    /**
     * 查找标签
     * @param id id
     * @return Tag
     */
    Optional<Tag> selectById(int id);
}
