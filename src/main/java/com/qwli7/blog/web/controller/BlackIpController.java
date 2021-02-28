package com.qwli7.blog.web.controller;

import com.qwli7.blog.entity.BlackIp;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.service.BlackIpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@Authenticated
@RestController
@RequestMapping("api")
public class BlackIpController {

    private final BlackIpService blackIpService;

    public BlackIpController(BlackIpService blackIpService) {
        this.blackIpService = blackIpService;
    }


    @GetMapping("blackips")
    public List<BlackIp> getAllBlackIps() {
        return blackIpService.selectAll();
    }

    @PostMapping("blackip")
    public ResponseEntity<?> save(@RequestBody @Valid BlackIp blackIp) {
        blackIpService.save(blackIp);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("blackip/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        blackIpService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
