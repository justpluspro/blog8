package com.qwli7.blog.web.controller;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.entity.Tag;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author qwli7
 * @date 2021/2/22 13:11
 * 功能：blog8
 **/
//@Authenticated
@RestController
@RequestMapping("api")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        super();
        this.categoryService = categoryService;
    }

    @GetMapping("categories")
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping("category/{id}")
    public ResponseEntity<Category>selectById(@PathVariable("id") int id) {
        final Category category = categoryService.selectById(id).orElseThrow(() ->
                new ResourceNotFoundException("category.notExists", "分类不存在"));
        return ResponseEntity.ok(category);
    }

    @PostMapping("category")
    public ResponseEntity<?> save(@RequestBody @Valid Category category) {
        categoryService.saveCategory(category);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("category/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("category/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody @Valid Category category) {
        category.setId(id);
        categoryService.updateCategory(category);
        return ResponseEntity.noContent().build();
    }
}
