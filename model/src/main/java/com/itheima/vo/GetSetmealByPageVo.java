package com.itheima.vo;

import com.itheima.entity.Order;
import com.itheima.entity.Setmeal;
import lombok.Data;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/12 13:24
 */
@Data
public class GetSetmealByPageVo {
    private List<Setmeal> records;
    private Integer total;
    private Integer size;
    private Integer current;
}
