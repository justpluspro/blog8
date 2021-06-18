package com.qwli7.blog.web.controller;

import com.qwli7.blog.BlogProperties;
import com.qwli7.blog.entity.BlackIp;
import com.qwli7.blog.entity.dto.PageDto;
import com.qwli7.blog.entity.vo.CommonQueryParam;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.service.BlackIpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 黑名单
 * @author liqiwen
 * @since 1.0
 */
@Authenticated
@RestController
@RequestMapping("api")
public class BlackIpController {

    private final BlackIpService blackIpService;
    private final BlogProperties blogProperties;

    public BlackIpController(BlackIpService blackIpService, BlogProperties blogProperties) {
        this.blackIpService = blackIpService;
        this.blogProperties = blogProperties;
    }

    /**
     * 获取所有的黑名单列表
     * @param queryParam queryParam
     * @return PageDto
     */
    @GetMapping("blackips")
    public PageDto<BlackIp> findPage(CommonQueryParam queryParam) {
        if(queryParam.hasNoSize()) {
            queryParam.setSize(blogProperties.getDefaultPageSize());
        }
        return blackIpService.findPage(queryParam);
    }

    /**
     * 保存黑名单
     * @param blackIp blackIp
     * @return ResponseEntity
     */
    @PostMapping("blackip")
    public ResponseEntity<?> save(@RequestBody @Valid BlackIp blackIp) {
        blackIpService.save(blackIp);
        return ResponseEntity.noContent().build();
    }

    /**
     * 删除黑名单
     * @param id id
     * @return ResponseEntity
     */
    @DeleteMapping("blackip/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        blackIpService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
