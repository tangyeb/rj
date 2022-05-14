package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.entity.ShoppingCart;
import com.itheima.vo.ShoppingCartVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/13 20:02
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}
