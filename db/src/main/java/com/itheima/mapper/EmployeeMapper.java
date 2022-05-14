package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author tang
 * @date 2022/5/7 19:22
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
