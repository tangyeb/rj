package com.itheima.dto;

import com.itheima.entity.SetmealDishes;
import lombok.Data;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/12 8:50
 */
@Data
public class SavemealDto {
    private String name;
    private String categoryId;
    private Double price;
    private String image;
    private String description;
    private int status;
    private List<SetmealDishes> setmealDishes;
}
