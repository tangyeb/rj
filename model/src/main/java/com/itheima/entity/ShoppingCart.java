package com.itheima.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tang
 * @date 2022/5/13 19:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    private String id;
    private String name;
    private String userId;
    private String dishId;
    private String setmealId;
    private String dishFlavor;
    private Integer number;
    private Double amount;
    private String image;
    private Date createTime;
}
