package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.entity.Dish;
import com.itheima.entity.Employee;
import com.itheima.mapper.DishMapper;
import com.itheima.service.DishService;
import com.itheima.vo.GetDishByPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author tang
 * @date 2022/5/9 20:31
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;


    @Override
    public boolean saveDish(Dish dish) {
        return dishMapper.insert(dish) > 0;
    }

    @Override
    public boolean update(Dish dish) {
        return dishMapper.updateById(dish) > 0;
    }

    @Override
    public int delete(String[] ids) {

        return dishMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public boolean changeStatusTo0(String[] ids) {
        return dishMapper.changeStatusTo0(ids);
    }

    @Override
    public boolean changeStatusTo1(String[] ids) {
        return dishMapper.changeStatusTo1(ids);
    }

    @Override
    public List<Dish> selectByCategoryId(String categoryId) {
        QueryWrapper<Dish> qw=new QueryWrapper<>();
        qw.eq("category_id", categoryId);
        return dishMapper.selectList(qw);
    }

    @Override
    public GetDishByPageVo selectAll(String name, Integer page, Integer pageSize, String type) {
        QueryWrapper<Dish> qw=new QueryWrapper<>();
        qw.like("name", name);
        qw.eq("type", type);
        qw.orderByAsc("sort");

        IPage<Dish> p = new Page<>(page, pageSize);
        //2 执行分页查询
        IPage<Dish> dishIPage = dishMapper.selectPage(p, qw);
        GetDishByPageVo getDishByPageVo=new GetDishByPageVo();
        getDishByPageVo.setRecords(dishIPage.getRecords());
        getDishByPageVo.setTotal(p.getTotal());
        getDishByPageVo.setSize(pageSize);
        getDishByPageVo.setCurrent(p.getCurrent());
        getDishByPageVo.setOrders(p.orders());
        getDishByPageVo.setOptimizeCountSql(true);
        getDishByPageVo.setHitCount(false);
        getDishByPageVo.setCountId(null);
        getDishByPageVo.setMaxLimit(null);
        getDishByPageVo.setSearchCount(true);
        getDishByPageVo.setPages(p.getPages());
        return  getDishByPageVo;
    }

    @Override
    public Dish selectById(String id) {
        return dishMapper.selectById(id);
    }

    @Override
    public List<Dish> selectByCategoryIdAndStatus(String categoryId, Integer status) {
        QueryWrapper<Dish> qw=new QueryWrapper<>();
        qw.eq("category_id", categoryId);
        qw.eq("status", 1);
        return dishMapper.selectList(qw);
    }


}
