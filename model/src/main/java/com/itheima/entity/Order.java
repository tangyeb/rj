package com.itheima.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author tang
 * @date 2022/5/11 14:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order {
    private String id;
    private String number;
    private Integer status;
    private String userId;
    private String addressBookId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date orderTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm")
    private Date checkoutTime;
    private Integer payMethod;
    private Double amount;
    private String remark;
    private String userName;
    private String phone;
    private String address;
    private String consignee;
}
