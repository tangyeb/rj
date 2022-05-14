package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tang
 * @date 2022/5/13 14:52
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
