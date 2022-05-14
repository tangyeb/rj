package com.itheima.service.impl;

import com.itheima.entity.SetmealDishes;
import com.itheima.mapper.SetmealDishesMapper;
import com.itheima.service.SetmealDishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/12 9:31
 */
@Service
public class SetmealDishesServiceImpl implements SetmealDishesService {
    @Autowired
    private SetmealDishesMapper setmealDishesMapper;
    @Override
    public boolean saveSetmealDishes(SetmealDishes setmealDishes) {
        setmealDishesMapper.saveSetmealDishes(setmealDishes);
        return true;
    }

    @Override
    public List<SetmealDishes> selectBySetmealId(String setmealId) {
        return setmealDishesMapper.selectBySetmealId(setmealId);
    }

    @Override
    public boolean update(SetmealDishes setmealDishes) {
        setmealDishesMapper.update(setmealDishes);
        return true;
    }

    @Override
    public boolean delete(String id) {

       setmealDishesMapper.delete(id);
       return true;
    }

    @Override
    public String selectNameById(String id) {
        return setmealDishesMapper.selectNameById(id);
    }
}
