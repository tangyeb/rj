package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.entity.CategoryInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tang
 * @date 2022/5/4 16:29
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryInfo> {

}
