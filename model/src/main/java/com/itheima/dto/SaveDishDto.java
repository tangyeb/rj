package com.itheima.dto;

import com.itheima.entity.Flavors;
import lombok.Data;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/9 21:12
 */
@Data
public class SaveDishDto {
    private String name;
    private Double price;
    private String code;
    private String image;
    private String description;
    private int status;
    private String categoryId;
    private List<Flavors> flavors;
}
