package com.qwli7.blog.web.controller;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.exception.ResourceNotFoundException;
import com.qwli7.blog.security.Authenticated;
import com.qwli7.blog.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author qwli7
 * 2021/2/22 13:11
 * 功能：CategoryController
 **/
@Authenticated
@RestController
@RequestMapping("api")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        super();
        this.categoryService = categoryService;
    }

    /**
     * 获取全部的分类列表
     * @return List
     */
    @GetMapping("categories")
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    /**
     * 获取分类以编辑
     * @param id id
     * @return ResponseEntity
     */
    @GetMapping("category/{id}")
    public ResponseEntity<Category> getCategoryForEdit(@PathVariable("id") int id) {
        final Category category = categoryService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("category.notExists", "分类不存在"));
        return ResponseEntity.ok(category);
    }

    /**
     * 保存动态
     * @param category category
     * @return ResponseEntity
     */
    @PostMapping("category")
    public ResponseEntity<?> save(@RequestBody Category category) {
        categoryService.save(category);
        return ResponseEntity.noContent().build();
    }

    /**
     * 删除分类
     * @param id id
     * @return ResponseEntity
     */
    @DeleteMapping("category/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 更新分类
     * @param id id
     * @param category category
     * @return ResponseEntity
     */
    @PutMapping("category/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody @Valid Category category) {
        category.setId(id);
        categoryService.update(category);
        return ResponseEntity.noContent().build();
    }
}
