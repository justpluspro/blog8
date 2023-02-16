package com.qwli7.blog.web.controller.api;

import com.qwli7.blog.entity.dto.CategoryDto;
import com.qwli7.blog.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author qwli7 
 * @date 2023/2/16 17:38
 * 功能：blog8
 **/
@RestController
@RequestMapping("api")
public class CategoryApiController {


    private final CategoryService categoryService;

    public CategoryApiController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoryDtos = categoryService.getAll();
        return ResponseEntity.ok(categoryDtos);
    }
}
