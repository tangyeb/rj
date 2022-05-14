package com.itheima.vo;

import lombok.Data;

/**
 * @author tang
 * @date 2022/5/7 19:57
 */
@Data
public class GetEmployeeByPageVo {
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
