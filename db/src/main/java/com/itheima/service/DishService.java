package com.itheima.service;

import com.itheima.entity.Dish;
import com.itheima.vo.GetDishByPageVo;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/9 20:31
 */
public interface DishService {
    public boolean saveDish(Dish dish);

    public boolean update(Dish dish);

    public int delete(String[] ids);

    public boolean changeStatusTo0(String[] ids);
    public boolean changeStatusTo1(String[] ids);

    public List<Dish> selectByCategoryId(String categoryId);

    public GetDishByPageVo selectAll(String name, Integer page, Integer pageSize, String type);

    public Dish selectById(String id);

    public List<Dish> selectByCategoryIdAndStatus(String categoryId,Integer status);

}
