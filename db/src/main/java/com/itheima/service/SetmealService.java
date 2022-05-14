package com.itheima.service;

import com.itheima.entity.Dish;
import com.itheima.entity.Setmeal;
import com.itheima.vo.GetSetmealByPageVo;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/12 9:08
 */
public interface SetmealService {
    public boolean saveSetmeal(Setmeal setmeal);

    public GetSetmealByPageVo selectAll(String name,
                                        Integer page,
                                        Integer pageSize);

    public boolean delete(String[] ids);

    public boolean changeStatusTo0(String[] ids);
    public boolean changeStatusTo1(String[] ids);

    public Setmeal selectById(String id);

    public boolean update(Setmeal setmeal);

    public List<Setmeal> selectByCategoryIdAndStatus(String categoryId, Integer status);
}
