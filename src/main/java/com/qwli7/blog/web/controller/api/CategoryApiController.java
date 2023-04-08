package com.qwli7.blog.web.controller.api;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.entity.vo.CategoryQueryParams;
import com.qwli7.blog.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qwli7
 * @date 2023/2/16 17:38
 * 功能：分类管理
 **/
@RestController
@RequestMapping("api")
public class CategoryApiController {

    private final CategoryService categoryService;

    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("categories")
    public List<Category> queryCategories(CategoryQueryParams categoryQueryParams) {
        Boolean queryPrivate = categoryQueryParams.isQueryPrivate();
        if(queryPrivate == null) {
            categoryQueryParams.setQueryPrivate(false);
        }
        return categoryService.queryCategory(categoryQueryParams);
    }


    @PostMapping("category")
    public ResponseEntity<Void> addCategory(@RequestBody @Validated Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("category/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated Category category, @PathVariable("id") Integer id) {
        category.setId(id);
        categoryService.updateCategory(category);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("category/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("category/{id}")
    public ResponseEntity<Category> get(@PathVariable("id") Integer id) {
        Category category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }
}
