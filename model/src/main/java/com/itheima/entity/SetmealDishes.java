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
 * @date 2022/5/12 8:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SetmealDishes implements Serializable {
    private String id;
    @TableField(value = "set_meal_id")
    private String setmealId;
    @TableField(value = "dish_id")
    private String dishId;
    private String name;
    private Double price;
    private Integer copies;
    private Integer sort;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date updateTime;
    private String createUser;
    private String updateUser;
    @TableLogic
    private int deleted;

}
