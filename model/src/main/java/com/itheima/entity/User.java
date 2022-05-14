package com.itheima.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tang
 * @date 2022/5/13 8:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String phone;
    private String name;
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date updateTime;
    private String createUser;
    private String updateUser;
    @TableField(value = "is_deleted")
    private int deleted;
}
