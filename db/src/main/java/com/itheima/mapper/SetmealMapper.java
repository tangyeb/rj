package com.itheima.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.entity.Order;
import com.itheima.entity.Setmeal;
import com.itheima.vo.GetSetmealByPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author tang
 * @date 2022/5/12 9:07
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    @Select("insert into tb_setmeal values (#{id},#{type},#{categoryId},#{name},#{price},#{status},#{code},#{description},#{image},#{createTime},#{updateTime},#{createUser},#{updateUser},#{categoryName},#{deleted})")
    @ResultMap("setmealResultMap")
    public void saveSetmeal(Setmeal setmeal);

    public List<Setmeal> selectAll(@Param("name") String name,
                                   @Param("page") Integer page,
                                   @Param("pageSize") Integer pageSize);

    @Select("select count(*) from tb_setmeal where deleted=0 ")
    public int selectCount();

    public void delete(String[] ids);

    boolean changeStatusTo1(String[] ids);

    boolean changeStatusTo0(String[] ids);

    public Setmeal selectById(String id);

    boolean update(Setmeal setmeal);
}
