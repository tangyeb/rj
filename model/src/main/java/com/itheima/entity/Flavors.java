package com.itheima.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.util.Date;


/**
 * @author tang
 * @date 2022/5/9 20:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Flavors implements Serializable {
    private String id;
    @TableField(value = "dish_id")
    private String dishId;
    private String name;
    private String value;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date updateTime;
    private String createUser;
    private String updateUser;
    private boolean showOption;
    @TableLogic
    private int isDeleted;


}
