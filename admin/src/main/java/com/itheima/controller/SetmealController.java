package com.itheima.controller;

import cn.hutool.core.util.IdUtil;
import com.itheima.common.R;
import com.itheima.dto.SavemealDto;
import com.itheima.dto.UpdateSetmealDto;
import com.itheima.entity.Setmeal;
import com.itheima.entity.SetmealDishes;
import com.itheima.handler.MyMetaObjecthandler;
import com.itheima.service.CategoryService;
import com.itheima.service.DishService;
import com.itheima.service.SetmealDishesService;
import com.itheima.service.SetmealService;
import com.itheima.vo.GetSetmealByIdVo;
import com.itheima.vo.GetSetmealByPageVo;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author tang
 * @date 2022/5/12 9:09
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishesService setmealDishesService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishService dishService;
    @Autowired
    private MyMetaObjecthandler mm;

    /**
     * 添加套餐
     *
     * @param dto 传入参数
     * @return success成功 error失败信息
     */
    @PostMapping
    public R<Object> saveSetmeal(@RequestBody SavemealDto dto) {
        if (Objects.nonNull(dto)) {
            Setmeal setmeal = new Setmeal();
            //生成ID
            BeanUtils.copyProperties(dto, setmeal);
            String snowflakeNextIdStr = IdUtil.getSnowflakeNextIdStr();
            setmeal.setId(snowflakeNextIdStr);

            //自动填充
            MetaObject metaObject = SystemMetaObject.forObject(setmeal);
            mm.insertFill(metaObject);

            //传入分类名
            setmeal.setCategoryName(categoryService.getNameById(dto.getCategoryId()));

            //传入分类
            setmeal.setType(categoryService.getTypeById(dto.getCategoryId()));
            //获取菜品数据集合进行遍历添加到数据库
            List<SetmealDishes> list = dto.getSetmealDishes();
            ArrayList<SetmealDishes> list1 = new ArrayList<>();
            for (SetmealDishes s : list) {
                SetmealDishes setmealDishes = new SetmealDishes();
                setmealDishes.setDishId(s.getDishId());
                setmealDishes.setSetmealId(setmeal.getId());
                setmealDishes.setCopies(s.getCopies());
                setmealDishes.setName(s.getName());
                setmealDishes.setPrice(s.getPrice());
                //ID
                setmealDishes.setId(IdUtil.getSnowflakeNextIdStr());
                //添加序号
                setmealDishes.setSort(dishService.selectById(s.getDishId()).getSort());
                //自动填充
                MetaObject metaSetmealDishes = SystemMetaObject.forObject(setmealDishes);
                mm.insertFill(metaSetmealDishes);
                //添加到数据库
                setmealDishesService.saveSetmealDishes(setmealDishes);
                //添加到新集合
                list1.add(setmealDishes);
            }
            //菜品集合添加到菜品、存入数据库
            setmeal.setSetmealDishes(list1);
            boolean flag = setmealService.saveSetmeal(setmeal);
            if (flag) {
                return R.success(setmeal);
            } else {
                return R.error("套餐已存在");
            }
        }
        return R.error("参数为空");
    }

    /**
     * 分页查询
     *
     * @param name     套餐名称
     * @param page     页码
     * @param pageSize 页码条数
     * @return success成功 error失败信息
     */
    @GetMapping("/page")
    public R<Object> selectByPage(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                  @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        GetSetmealByPageVo getSetmealByPageVo = setmealService.selectAll(name, page, pageSize);
        return R.success(getSetmealByPageVo);

    }

    /**
     * 删除套餐
     *
     * @param ids 套餐ID
     * @return success成功 error失败信息
     */
    @DeleteMapping("")
    public R<String> deleteDish(@RequestParam String[] ids) {
        if (Objects.nonNull(ids)) {
            boolean flag = setmealService.delete(ids);
            if (flag) {
                return R.success("成功");
            }
            return R.error("403");
        }
        return R.error("204");
    }

    /**
     * 批量停售
     *
     * @param ids 套餐ID数组
     * @return success成功信息 error错误信息
     */
    @PostMapping("/status/0")
    public R<String> changeStatusTo0(@RequestParam String[] ids) {
        if (Objects.nonNull(ids)) {
            boolean flag = setmealService.changeStatusTo0(ids);
            if (flag) {
                return R.success("操作成功");
            }
            return R.error("401");
        }
        return R.error("404");
    }

    /**
     * 批量起售
     *
     * @param ids 套餐ID数组
     * @return success成功信息 error错误信息
     */
    @PostMapping("/status/1")
    public R<String> changeStatusTo1(@RequestParam String[] ids) {
        if (Objects.nonNull(ids)) {
            boolean flag = setmealService.changeStatusTo1(ids);
            if (flag) {
                return R.success("操作成功");
            }
            return R.error("401");
        }
        return R.error("404");
    }

    /**
     * 根据ID查询
     *
     * @param id 套餐ID
     * @return success成功 error失败信息
     */
    @GetMapping("/{id}")
    public R getSetmealById(@PathVariable String id) {
        if (id != null) {
            Setmeal setmeal = setmealService.selectById(id);
            GetSetmealByIdVo vo = new GetSetmealByIdVo();
            BeanUtils.copyProperties(setmeal, vo);
            List<SetmealDishes> list = setmealDishesService.selectBySetmealId(id);
            vo.setSetmealDishes(list);
            return R.success(vo);
        }
        return R.error("404");
    }


    /**
     * 修改套餐信息
     *
     * @param dto 传入参数
     * @return success成功 error失败信息
     */
    @PutMapping("")
    public R<String> updateSetmeal(@RequestBody UpdateSetmealDto dto) {
        if (Objects.nonNull(dto)) {
            //传入参数拷贝到新建对象
            Setmeal setmeal = new Setmeal();
            BeanUtils.copyProperties(dto, setmeal);
            //自动注入
            MetaObject metaObject = SystemMetaObject.forObject(setmeal);
            mm.updateFill(metaObject);
            //获取原菜品
            List<SetmealDishes> list = setmealDishesService.selectBySetmealId(dto.getId());
            //获取新菜品
            List<SetmealDishes> setmealDishes = setmeal.getSetmealDishes();
            //判断菜品名是否存在
            boolean flag = true;
            //新菜品与原菜品比对
            for (SetmealDishes setmealDish : setmealDishes) {
                for (SetmealDishes value : list) {
                    String s = setmealDishesService.selectNameById(value.getId());
                    //存在更新
                    if (setmealDish.getName().equals(s)) {
                        MetaObject metaSetmealDish = SystemMetaObject.forObject(setmealDish);
                        mm.updateFill(metaSetmealDish);
                        setmealDishesService.update(setmealDish);
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    //不存在新增
                    //生成ID
                    SetmealDishes setmealDishes1 = new SetmealDishes();
                    BeanUtils.copyProperties(setmealDish, setmealDishes1);
                    setmealDishes1.setSetmealId(setmeal.getId());
                    setmealDishes1.setId(IdUtil.getSnowflakeNextIdStr());
                    MetaObject metas1 = SystemMetaObject.forObject(setmealDishes1);
                    mm.insertFill(metas1);
                    setmealDishesService.saveSetmealDishes(setmealDishes1);
                }
                flag = true;
            }
            // 重新获取修改后的菜品集合
            List<SetmealDishes> list1 = setmealDishesService.selectBySetmealId(dto.getId());
            boolean flag1 = true;
            for (SetmealDishes value : list1) {
                String s1 = setmealDishesService.selectNameById(value.getId());
                for (SetmealDishes sm1 : setmealDishes) {
                    //如果名字有就跳过
                    if (s1.equals(sm1.getName())) {
                        flag1 = false;
                        break;
                    }
                    //菜品名不存在就删除
                }
                if (flag1) {
                    setmealDishesService.delete(value.getId());
                }
                flag1 = true;
            }
            boolean result = setmealService.update(setmeal);
            if (result) {
                return R.success("修改套餐成功");
            }
            return R.error("403");
        }
        return R.error("404");
    }

}