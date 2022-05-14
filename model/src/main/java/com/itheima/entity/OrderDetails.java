package com.itheima.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author tang
 * @date 2022/5/14 10:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderDetails {
    private String id;
    private String orderId;
    private String name;
    private Integer number;
    private String dishId;
    private String setmealId;
    @TableField(value = "dish_flavor")
    private String dishFlavor;
    private Double amount;
    private Double money;
    private String image;

}
