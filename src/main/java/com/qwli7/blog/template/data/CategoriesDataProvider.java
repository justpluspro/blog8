package com.qwli7.blog.template.data;

import com.qwli7.blog.entity.Category;
import com.qwli7.blog.service.CategoryService;

import java.util.List;
import java.util.Map;

/**
 * 分类数据列表数据提供者
 * @author liqiwen
 * @since 2.1
 */
public class CategoriesDataProvider extends AbstractDataProvider<List<Category>>{

    private final CategoryService categoryService;

    public CategoriesDataProvider(CategoryService categoryService) {
        super("categories");
        this.categoryService = categoryService;
    }

    @Override
    public List<Category> queryData(Map<String, String> attributeMap) {
        return categoryService.getAll();
    }
}
