package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/11 14:17
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    public List<Order> selectAll(@Param("beginTime") String beginTime,
                                 @Param("endTime") String endTime,
                                 @Param("number") String number,
                                 @Param("page") Integer page,
                                 @Param("pageSize") Integer pageSize);

    @Select("select count(*) from tb_order where deleted=0 ")
    public int selectCount();
}
