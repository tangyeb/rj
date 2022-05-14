package com.itheima.service;

import com.itheima.entity.SetmealDishes;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/12 9:31
 */
public interface SetmealDishesService {
    public boolean saveSetmealDishes(SetmealDishes setmealDishes);

    public List<SetmealDishes> selectBySetmealId(String id);

    public boolean update(SetmealDishes setmealDishes);

    public boolean delete(String id);

    public String selectNameById(String id);
}
