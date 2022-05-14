package com.itheima.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.itheima.common.R;
import com.itheima.entity.Dish;
import com.itheima.entity.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/13 18:58
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 获取套餐列表
     *
     * @param categoryId 分类ID
     * @param status     状态
     * @return success 套餐列表  error 错误信息
     */
    @GetMapping("/list")
    public R<Object> getDishList(@RequestParam String categoryId,
                                 @RequestParam(value = "status", defaultValue = "1") Integer status) {
        if (StrUtil.isEmpty(categoryId)) {
            return R.error("参数为空");
        }

        //根据分类ID和状态获取
        List<Setmeal> setmeals = setmealService.selectByCategoryIdAndStatus(categoryId, status);

        if (ObjectUtil.isNotEmpty(setmeals)) {
            return R.success(setmeals);
        }

        return R.error("查无数据");
    }
}
