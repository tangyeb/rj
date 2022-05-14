package com.itheima.controller;

import com.itheima.common.R;
import com.itheima.entity.CategoryInfo;
import com.itheima.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/4 17:01
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类列表
     *
     * @return 列表集合
     */
    @GetMapping("/list")
    public R<Object> getAll() {
        List<CategoryInfo> list = categoryService.getAll();
        return R.success(list);
    }
}