package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tang
 * @date 2022/5/9 20:20
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

    boolean changeStatusTo1(String[] ids);

    boolean changeStatusTo0(String[] ids);


}
