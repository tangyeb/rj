package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.entity.Order;
import com.itheima.vo.GetEmployeeByPageVo;
import com.itheima.vo.GetOrderByPageVo;

/**
 * @author tang
 * @date 2022/5/11 14:19
 */
public interface OrderService extends IService<Order> {

    public GetOrderByPageVo selectAll(String beginTime,
                                      String endTime,
                                      String number,
                                      Integer page,
                                      Integer pageSize);

    public void submit(Order order);

    public GetOrderByPageVo getOrderByPage(Integer page, Integer pageSize);

}
