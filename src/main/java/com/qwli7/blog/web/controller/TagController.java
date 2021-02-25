package com.qwli7.blog.web.controller;

import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author qwli7
 * @date 2021/2/22 13:11
 * 功能：blog8
 **/
@Authenticated
@RestController
@RequestMapping("api")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        super();
        this.tagService = tagService;
    }

    @GetMapping("tags")
    public List<Tag> getTags() {
        return tagService.findAllTags();
    }

    @PostMapping("tag")
    public ResponseEntity<?> save(@RequestBody @Valid Tag tag) {
        tagService.save(tag);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("tag/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("tag/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Tag tag) {
        tag.setId(id);
        tagService.updateTag(tag);
        return ResponseEntity.noContent().build();
    }
}
