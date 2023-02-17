package com.qwli7.blog.web.controller.api;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.dto.SavedMoment;
import com.qwli7.blog.entity.vo.MomentBean;
import com.qwli7.blog.entity.vo.MomentQueryParams;
import com.qwli7.blog.service.MomentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author qwli7
 * @date 2023/2/17 17:51
 * 功能：blog8
 **/
@RestController
@RequestMapping("api")
public class MomentApiController {


    private final MomentService momentService;

    public MomentApiController(MomentService momentService) {
        this.momentService = momentService;
    }


    @PostMapping("moment")
    public ResponseEntity<SavedMoment> createMoment(@RequestBody MomentBean momentBean) {
       SavedMoment savedMoment = momentService.saveMoment(momentBean);
       return ResponseEntity.ok(savedMoment);
    }


    @GetMapping("moments")
    public ResponseEntity<PageResult<Moment>> queryMoments(MomentQueryParams momentQueryParams) {
        PageResult<Moment> momentPageResult = momentService.queryMoments(momentQueryParams);
        return ResponseEntity.ok(momentPageResult);
    }
}
