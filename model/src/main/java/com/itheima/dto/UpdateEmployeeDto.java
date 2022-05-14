package com.itheima.dto;

import lombok.Data;

/**
 * @author tang
 * @date 2022/5/7 20:30
 */
@Data
public class UpdateEmployeeDto {
    private String id;
    private String username;
    private String name;
    private String password;
    private String phone;
    private String sex;
    private String idNumber;
    private Integer status;
    private String createTime;
    private String updateTime;
    private String createUser;
    private String updateUser;
}
