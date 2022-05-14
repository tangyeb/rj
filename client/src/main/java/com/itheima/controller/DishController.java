package com.itheima.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.itheima.common.R;
import com.itheima.entity.Dish;
import com.itheima.service.DishService;
import com.itheima.service.FlavorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/13 13:34
 */
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private FlavorsService flavorsService;

    /**
     * 获取菜品列表
     *
     * @param categoryId 分类ID
     * @param status     状态
     * @return 菜品列表
     */
    @GetMapping("/list")
    public R<Object> getDishList(@RequestParam String categoryId,
                                 @RequestParam(value = "status", defaultValue = "1") Integer status) {
        if (StrUtil.isEmpty(categoryId)) {
            return R.error("参数为空");
        }
        List<Dish> dishes = dishService.selectByCategoryIdAndStatus(categoryId, status);
        dishes.forEach(dish -> dish.setFlavors(flavorsService.selectByDishId(dish.getId())));
        if (ObjectUtil.isNotEmpty(dishes)) {
            return R.success(dishes);
        }
        return R.error("查无数据");
    }

    /**
     * 获取菜品详情
     *
     * @param id 菜品ID
     * @return 菜品详情
     */
    @GetMapping("/{id}")
    public R<Object> getDishById(@PathVariable String id) {
        if (StrUtil.isEmpty(id)) {
            return R.error("参数为空");
        }

        Dish dish = dishService.selectById(id);

        if (ObjectUtil.isNotEmpty(dish)) {
            dish.setFlavors(flavorsService.selectByDishId(dish.getId()));
            return R.success(dish);
        }
        return R.error("查无数据");
    }
}
