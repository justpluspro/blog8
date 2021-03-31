package com.qwli7.blog.web.controller;

import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.MomentQueryParam;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.service.MomentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author qwli7
 * 2021/2/22 13:11
 * 功能：MomentController
 **/
@Authenticated
@RestController
@RequestMapping("api")
public class MomentController {

    private final MomentService momentService;
    private final BlogProperties blogProperties;

    public MomentController(MomentService momentService, BlogProperties blogProperties) {
        this.momentService = momentService;
        this.blogProperties = blogProperties;
    }

    /**
     * 查询动态
     * @param queryParam queryParam
     * @return 动态列表
     */
    @GetMapping("moments")
    public ResponseEntity<PageDto<Moment>> selectPage(MomentQueryParam queryParam) {
        Boolean orderDesc = queryParam.getOrderDesc();
        if(orderDesc == null) {
            orderDesc = true;
        }
        queryParam.setOrderDesc(orderDesc);
        int page = queryParam.getPage();
        if(page < 1) {
            queryParam.setPage(1);
        }
        if(queryParam.hasNoSize()) {
            queryParam.setSize(blogProperties.getDefaultPageSize());
        }
        return ResponseEntity.ok(momentService.selectPage(queryParam));
    }

    /**
     * 创建动态
     * @param moment moment
     * @return ResponseEntity
     */
    @PostMapping("moment")
    public ResponseEntity<Integer> create(@RequestBody @Valid Moment moment) {
        return ResponseEntity.ok(momentService.saveMoment(moment));
    }

    /**
     * 修改动态
     * @param id id
     * @param moment moment
     * @return ResponseEntity
     */
    @PutMapping("moment/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id,
                                    @RequestBody @Valid Moment moment) {
        moment.setId(id);
        momentService.update(moment);
        return ResponseEntity.noContent().build();
    }

    /**
     * 删除评论
     * @param id id
     * @return ResponseEntity
     */
    @DeleteMapping("moment/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        momentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 获取动态用以编辑
     * @param id id
     * @return Moment
     */
    @GetMapping("moment/{id}")
    public Moment getMomentForEdit(@PathVariable("id") int id) {
        return momentService.getMomentForEdit(id).orElseThrow(()
                -> new ResourceNotFoundException("moment.notExists", "动态不存在"));
    }
}
