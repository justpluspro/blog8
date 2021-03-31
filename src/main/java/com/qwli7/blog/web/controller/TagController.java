package com.qwli7.blog.web.controller;

import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.CommonQueryParam;
import com.qwli7.blog.entity.vo.TagQueryParam;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author qwli7
 * 2021/2/22 13:11
 * 功能：TagController
 **/
@Authenticated
@RestController
@RequestMapping("api")
public class TagController {

    private final TagService tagService;
    private final BlogProperties blogProperties;

    public TagController(TagService tagService, BlogProperties blogProperties) {
        super();
        this.tagService = tagService;
        this.blogProperties = blogProperties;
    }

    /**
     * 分类获取标签列表
     * @param queryParam queryParam
     * @return PageDto
     */
    @GetMapping("tags")
    public PageDto<Tag> selectPage(CommonQueryParam queryParam) {
        if(queryParam.hasNoSize()) {
            queryParam.setSize(blogProperties.getDefaultPageSize());
        }
        return tagService.selectPage(queryParam);
    }

    /**
     * 根据 id 获取标签
     * @param id id
     * @return Tag
     */
    @GetMapping("tag/{id}")
    public Tag selectById(@PathVariable("id") int id) {
        return tagService.selectById(id).orElseThrow(()
                -> new ResourceNotFoundException("tag.notExists","标签不存在"));
    }

    /**
     * 保存标签
     * @param tag tag
     * @return ResponseEntity
     */
    @PostMapping("tag")
    public ResponseEntity<?> save(@RequestBody @Valid Tag tag) {
        tagService.save(tag);
        return ResponseEntity.noContent().build();
    }

    /**
     * 删除标签
     * @param id id
     * @return ResponseEntity
     */
    @DeleteMapping("tag/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 更新标签
     * @param id id
     * @param tag tag
     * @return ResponseEntity
     */
    @PutMapping("tag/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Tag tag) {
        tag.setId(id);
        tagService.updateTag(tag);
        return ResponseEntity.noContent().build();
    }
}
