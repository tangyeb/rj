package com.itheima.vo;

import com.itheima.entity.Order;
import lombok.Data;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/11 14:29
 */
@Data
public class GetOrderByPageVo {
    private Object records;
    private Number total;
    private Number size;
    private Number current;
    private Object orders;
    private Boolean optimizeCountSql;
    private Boolean hitCount;
    private Object countId;
    private Object maxLimit;
    private Boolean searchCount;
    private Number pages;
}
