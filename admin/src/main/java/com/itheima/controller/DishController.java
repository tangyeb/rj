package com.itheima.controller;

import cn.hutool.core.util.IdUtil;
import com.itheima.common.R;
import com.itheima.dto.SaveDishDto;
import com.itheima.dto.UpdateDishDto;
import com.itheima.entity.Dish;
import com.itheima.entity.Flavors;
import com.itheima.handler.MyMetaObjecthandler;
import com.itheima.service.CategoryService;
import com.itheima.service.DishService;
import com.itheima.service.FlavorsService;
import com.itheima.vo.GetDishByIdVo;
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
 * @date 2022/5/9 20:13
 */
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private FlavorsService flavorsService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MyMetaObjecthandler mm;

    /**
     * 新增菜品
     *
     * @param dto 传入参数
     * @return success新增对象 error错误信息
     */
    @PostMapping("")
    public R saveDish(@RequestBody SaveDishDto dto) {
        if (Objects.nonNull(dto)) {
            Dish dish = new Dish();
            //生成ID
            BeanUtils.copyProperties(dto, dish);
            String snowflakeNextIdStr = IdUtil.getSnowflakeNextIdStr();
            dish.setId(snowflakeNextIdStr);

            //自动填充
            MetaObject metaObject = SystemMetaObject.forObject(dish);
            mm.insertFill(metaObject);

            //传入序号
            dish.setSort(categoryService.getSortById(dto.getCategoryId()));
            //传入分类名
            dish.setCategoryName(categoryService.getNameById(dto.getCategoryId()));

            //传入分类
            dish.setType(categoryService.getTypeById(dto.getCategoryId()));
            //获取口味数据集合进行遍历添加到数据库
            List<Flavors> list = dto.getFlavors();
            ArrayList<Flavors> flavorsList = new ArrayList<>();
            for (Flavors f : list) {
                Flavors flavors = new Flavors();
                flavors.setDishId(dish.getId());
                flavors.setName(f.getName());
                flavors.setValue(f.getValue());
                flavors.setShowOption(f.isShowOption());
                //自动填充
                MetaObject metaFlavors = SystemMetaObject.forObject(flavors);
                mm.insertFill(metaFlavors);
                //添加到数据库
                flavorsService.saveFlavors(flavors);
                //添加到新集合
                flavorsList.add(flavors);
            }
            //口味集合添加到菜品、存入数据库
            dish.setFlavors(flavorsList);
            boolean flag = dishService.saveDish(dish);
            if (flag) {
                return R.success(dish);
            } else {
                return R.error("菜品已存在");
            }
        }
        return R.error("参数为空");
    }

    /**
     * 更新菜品
     *
     * @param dto 传入参数
     * @return success成功信息 error错误信息
     */
    @PutMapping("")
    public R<String> updateDish(@RequestBody UpdateDishDto dto) {
        if (Objects.nonNull(dto)) {
            Dish dish = new Dish();
            BeanUtils.copyProperties(dto, dish);
            //自动注入
            MetaObject metaObject = SystemMetaObject.forObject(dish);
            mm.updateFill(metaObject);
            //获取原口味
            List<Flavors> list = flavorsService.selectByDishId(dto.getId());
            //获取新口味
            List<Flavors> flavors = dish.getFlavors();
            //判断口味名是否存在
            boolean flag = true;
            for (Flavors flavor : flavors) {
                for (Flavors value : list) {
                    String s = flavorsService.selectNameById(value.getId());
                    //存在更新
                    if (flavor.getName().equals(s)) {
                        MetaObject metaFlavor = SystemMetaObject.forObject(flavor);
                        mm.updateFill(metaFlavor);
                        flavorsService.update(flavor);
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    //不存在新增
                    //生成ID
                    Flavors f = new Flavors();
                    BeanUtils.copyProperties(flavor, f);
                    f.setDishId(dish.getId());
                    MetaObject metaFlavor = SystemMetaObject.forObject(f);
                    mm.insertFill(metaFlavor);
                    flavorsService.saveFlavors(f);
                }
                flag = true;
            }
            // 重新获取修改后的口味集合
            List<Flavors> list1 = flavorsService.selectByDishId(dto.getId());
            boolean flag1 = true;
            for (Flavors value : list1) {
                String s1 = flavorsService.selectNameById(value.getId());
                for (Flavors flavor1 : flavors) {
                    //如果名字有就跳过
                    if (s1.equals(flavor1.getName())) {
                        flag1 = false;
                        break;
                    }
                    //口味名不存在就删除
                }
                if (flag1) {
                    flavorsService.delete(value.getId());
                }
                flag1 = true;
            }
            boolean result = dishService.update(dish);
            if (result) {
                return R.success("修改菜品成功");
            }
            return R.error("403");
        }
        return R.error("404");
    }

    /**
     * 删除菜品
     *
     * @param ids 菜品ID数组
     * @return success成功信息 error错误信息
     */
    @DeleteMapping("")
    public R<String> deleteDish(@RequestParam String[] ids) {
        if (Objects.nonNull(ids)) {
            int delete = dishService.delete(ids);
            if (delete == ids.length) {
                return R.success("成功");
            }
            return R.error("403");

        }
        return R.error("204");
    }

    /**
     * 批量停售
     *
     * @param ids 菜品ID数组
     * @return success成功信息 error错误信息
     */
    @PostMapping("/status/0")
    public R<String> changeStatusTo0(@RequestParam String[] ids) {
        if (Objects.nonNull(ids)) {
            boolean flag = dishService.changeStatusTo0(ids);
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
     * @param ids 菜品ID数组
     * @return success成功信息 error错误信息
     */
    @PostMapping("/status/1")
    public R<String> changeStatusTo1(@RequestParam String[] ids) {
        if (Objects.nonNull(ids)) {
            boolean flag = dishService.changeStatusTo1(ids);
            if (flag) {
                return R.success("操作成功");
            }
            return R.error("401");
        }
        return R.error("404");
    }

    /**
     * 菜品下拉列表
     *
     * @param categoryId 分类ID
     * @return success菜品分页后列表 error错误信息
     */
    @GetMapping("/list")
    public R getDishByCategoryId(@RequestParam String categoryId) {
        if (categoryId != null) {
            List<Dish> dishes = dishService.selectByCategoryId(categoryId);
            return R.success(dishes);
        }
        return R.error("404");
    }

    /**
     * 分页查询
     *
     * @param name     菜品名
     * @param type     类型
     * @param page     页码
     * @param pageSize 页码条数
     * @return 分页查询结果
     */
    @GetMapping("/page")
    public R getDishByPage(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                           @RequestParam(value = "type", required = false, defaultValue = "1") String type,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        return R.success(dishService.selectAll(name, page, pageSize, type));
    }

    /**
     * 根据菜品ID查询
     *
     * @param id 菜品ID
     * @return success菜品对象 error错误信息
     */
    @GetMapping("/{id}")
    public R getDishById(@PathVariable String id) {
        if (id != null) {
            Dish dish = dishService.selectById(id);
            GetDishByIdVo vo = new GetDishByIdVo();
            BeanUtils.copyProperties(dish, vo);
            List<Flavors> list = flavorsService.selectByDishId(id);
            vo.setFlavors(list);
            return R.success(vo);
        }
        return R.error("404");
    }
}
