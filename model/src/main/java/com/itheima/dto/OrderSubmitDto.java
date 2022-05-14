package com.itheima.dto;

import lombok.Data;

/**
 * @author tang
 * @date 2022/5/13 14:20
 */
@Data
public class OrderSubmitDto {
    private String remark;
    private Integer payMethod;
    private String addressBookId;
}
