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
import java.util.List;

/**
 * @author tang
 * @date 2022/5/12 8:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Setmeal implements Serializable {
    private String id;
    private int type;
    private String name;
    private String categoryId;
    private double price;
    private String code;
    private String image;
    private String description;
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date updateTime;
    private String createUser;
    private String updateUser;
    @TableField(exist = false)
    private List<SetmealDishes> setmealDishes;
    private String categoryName;
    @TableLogic
    private int deleted;
}
