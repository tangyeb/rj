package com.itheima.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.entity.Dish;
import com.itheima.entity.Setmeal;
import com.itheima.mapper.SetmealMapper;
import com.itheima.service.SetmealService;
import com.itheima.vo.GetSetmealByPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/12 9:08
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    public boolean saveSetmeal(Setmeal setmeal) {
        setmealMapper.saveSetmeal(setmeal);
        return true;
    }

    @Override
    public GetSetmealByPageVo selectAll(String name, Integer page, Integer pageSize) {
        name="%"+name+"%";
        Integer i=(page-1)*pageSize;
        List<Setmeal> setmeals = setmealMapper.selectAll(name, i, pageSize);
        GetSetmealByPageVo getSetmealByPageVo=new GetSetmealByPageVo();
        getSetmealByPageVo.setRecords(setmeals);
        getSetmealByPageVo.setTotal(setmealMapper.selectCount());
        getSetmealByPageVo.setSize(pageSize);
        getSetmealByPageVo.setCurrent(page);
        return getSetmealByPageVo;
    }

    @Override
    public boolean delete(String[] ids) {
        setmealMapper.delete(ids);
        return true;
    }

    @Override
    public boolean changeStatusTo0(String[] ids) {
        return setmealMapper.changeStatusTo0(ids);
    }

    @Override
    public boolean changeStatusTo1(String[] ids) {
        return setmealMapper.changeStatusTo1(ids);
    }

    @Override
    public Setmeal selectById(String id) {
        return setmealMapper.selectById(id);
    }

    @Override
    public boolean update(Setmeal setmeal) {

        return setmealMapper.update(setmeal);
    }

    @Override
    public List<Setmeal> selectByCategoryIdAndStatus(String categoryId, Integer status) {
        QueryWrapper<Setmeal> qw=new QueryWrapper<>();
        qw.eq("category_id", categoryId);
        qw.eq("status", 1);
        return setmealMapper.selectList(qw);
    }
}
