package com.itheima.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.common.R;
import com.itheima.common.TokenHolder;
import com.itheima.dto.AddGoodsDto;
import com.itheima.dto.SubGoodsDto;
import com.itheima.entity.Dish;
import com.itheima.entity.Setmeal;
import com.itheima.entity.ShoppingCart;
import com.itheima.service.DishService;
import com.itheima.service.SetmealService;
import com.itheima.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/13 19:11
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加到购物车
     *
     * @param dto 传入参数
     * @return success 添加对象 error 空参
     */
    @PostMapping("/add")
    public R<Object> addGoods(@RequestBody AddGoodsDto dto) {
        if (ObjectUtil.isEmpty(dto)) {
            return R.error("参数为空");
        }

        //新建购物车对象
        ShoppingCart shoppingCart = new ShoppingCart();

        //传入赋值
        BeanUtils.copyProperties(dto, shoppingCart);

        //生成ID、userId、createTime
        shoppingCart.setUserId(TokenHolder.getCurrentId());
        shoppingCart.setId(IdUtil.getSnowflakeNextIdStr());
        shoppingCart.setCreateTime(new DateTime());

        //获取参数菜品ID
        String dishId = dto.getDishId();
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();

        //条件：当前用户
        qw.eq(ShoppingCart::getUserId, TokenHolder.getCurrentId());

        //参数套餐ID为空，即为菜品
        if (dto.getSetmealId() == null) {
            //获取菜品
            Dish dish = dishService.selectById(dto.getDishId());

            //通过菜品获取图片设置到购物车对象中
            shoppingCart.setImage(dish.getImage());

            //条件：菜品ID
            qw.eq(ShoppingCart::getDishId, dishId);
        } else {
            //获取套餐
            Setmeal setmeal = setmealService.selectById(dto.getDishId());

            //通过套餐获取图片设置到购物车对象中
            shoppingCart.setImage(setmeal.getImage());

            //条件：套餐ID
            qw.eq(ShoppingCart::getSetmealId, dto.getSetmealId());

        }

        //通过当前用户 和 菜品ID/套餐ID 来判断购物车是否已经有该商品
        ShoppingCart one = shoppingCartService.getOne(qw);
        if (one != null) {
            //有就取出数量 添加1 并更新
            Integer number = one.getNumber();
            one.setNumber(number + 1);
            shoppingCartService.updateById(one);
        } else {
            //设置数量为1，新增
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);

            //重新赋值(可以直接返回one)
            one = shoppingCart;
        }

        return R.success(one);

    }

    /**
     * 提交订单
     *
     * @param dto 传入参数
     * @return success 成功结果
     */
    @PostMapping("/sub")
    public R<Object> subGoods(@RequestBody SubGoodsDto dto) {
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        qw.eq(ShoppingCart::getUserId, TokenHolder.getCurrentId());

        if (dto.getSetmealId() == null) {
            //菜品ID
            qw.eq(ShoppingCart::getDishId, dto.getDishId());
        } else {
            //套餐ID
            qw.eq(ShoppingCart::getSetmealId, dto.getSetmealId());
        }

        //查找该商品数量并-1
        ShoppingCart one = shoppingCartService.getOne(qw);
        Integer number = one.getNumber();
        one.setNumber(number - 1);
        shoppingCartService.updateById(one);
        return R.success(one);
    }

    /**
     * 获取购物车列表
     *
     * @return 列表
     */
    @GetMapping("/list")
    public R<Object> getList() {
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        qw.eq(ShoppingCart::getUserId, TokenHolder.getCurrentId());
        qw.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(qw);

        if (CollUtil.isNotEmpty(list)) {
            return R.success(list);
        }
        return R.error("购物车空荡荡");
    }

    /**
     * 清楚购物车
     *
     * @return 结果
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        qw.eq(ShoppingCart::getUserId, TokenHolder.getCurrentId());
        shoppingCartService.remove(qw);

        return R.success("清空成功");
    }
}
