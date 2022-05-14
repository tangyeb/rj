package com.itheima.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tang
 * @date 2022/5/9 20:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Dish implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private int type;
    private String name;
    @TableField(value = "category_id")
    private String categoryId;
    private double price;
    private String code;
    private String image;
    private String description;
    private int status;
    private int sort;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date updateTime;
    private String createUser;
    private String updateUser;
    @TableField(exist = false)
    private List<Flavors> flavors;
    private String categoryName;
    private String copies;
    private int deleted;
}
