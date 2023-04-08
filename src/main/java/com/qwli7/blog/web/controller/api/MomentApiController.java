package com.qwli7.blog.web.controller.api;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.dto.PageResult;
import com.qwli7.blog.entity.vo.MomentQueryParams;
import com.qwli7.blog.service.MomentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<Void> addMoment(@RequestBody @Validated Moment moment) {
        momentService.addMoment(moment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("moment/{id}")
    public ResponseEntity<Void> updateMoment(@PathVariable("id") Integer id, @RequestBody Moment moment) {
        moment.setId(id);
        momentService.updateMoment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("moment/{id}")
    public ResponseEntity<Void> deleteMoment(@PathVariable("id") Integer id) {
        momentService.deleteMoment(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("moments")
    public PageResult<Moment> findMoments(MomentQueryParams momentQueryParams) {
        return momentService.findMoments(momentQueryParams);
    }
}
