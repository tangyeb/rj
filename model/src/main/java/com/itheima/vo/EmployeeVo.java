package com.itheima.vo;

import com.itheima.entity.Employee;
import lombok.Data;

/**
 * @author tang
 * @date 2022/5/9 10:51
 */
@Data
public class EmployeeVo extends Employee {
    private String token;
}
