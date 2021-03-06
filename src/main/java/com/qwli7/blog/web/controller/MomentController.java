package com.qwli7.blog.web.controller;

import com.qwli7.blog.entity.Moment;
import com.qwli7.blog.entity.vo.MomentQueryParam;
import com.qwli7.blog.service.MomentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

/**
 * @author qwli7
 * @date 2021/2/22 13:11
 * 功能：blog8
 **/
@RestController
@RequestMapping("api")
public class MomentController {

    private final MomentService momentService;

    public MomentController(MomentService momentService) {
        this.momentService = momentService;
    }


    @GetMapping("moments")
    public ResponseEntity<?> query(MomentQueryParam queryParam) {
        Boolean orderDesc = queryParam.getOrderDesc();
        if(orderDesc == null) {
            orderDesc = true;
        }
        queryParam.setOrderDesc(orderDesc);
        int page = queryParam.getPage();
        if(page < 1) {
            queryParam.setPage(1);
        }
        int size = queryParam.getSize();
        if(size < 10) {
            queryParam.setSize(10);
        }
        return ResponseEntity.ok(momentService.selectPage(queryParam));
    }


    @PostMapping("moment")
    public ResponseEntity<?> create(@RequestBody @Valid Moment moment) {
        return ResponseEntity.ok(momentService.saveMoment(moment));
    }

    @PutMapping("moment/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody @Valid Moment moment) {
        moment.setId(id);
        momentService.update(moment);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("moment/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        momentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("moment/{id}")
    public Moment getMomentForEdit(@PathVariable("id") int id) {
        return momentService.getMomentForEdit(id);
    }


}
