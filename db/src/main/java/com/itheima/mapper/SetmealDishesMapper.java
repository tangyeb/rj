package com.itheima.mapper;

import com.itheima.entity.SetmealDishes;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/12 9:29
 */
@Mapper
public interface SetmealDishesMapper {
    @Select("insert into tb_setmeal_dishes values (#{id},#{setmealId},#{dishId},#{name},#{price},#{copies},#{sort},#{createTime},#{updateTime},#{createUser},#{updateUser},#{deleted})")
    @ResultMap("setmealDishesResultMap")
    public void saveSetmealDishes(SetmealDishes setmealDishes);

    @Select("select * from tb_setmeal_dishes where deleted= 0 and setmeal_id=#{setmealId}")
    public List<SetmealDishes> selectBySetmealId(@Param("setmealId") String setmealId);


    public void update(SetmealDishes setmealDishes);

    @Select("update tb_setmeal_dishes set deleted='1' where id=#{id}")
    public void delete(String id);

    @Select("select name from tb_setmeal_dishes where deleted= '0' and id=#{id}")
    public String selectNameById(String id);
}
