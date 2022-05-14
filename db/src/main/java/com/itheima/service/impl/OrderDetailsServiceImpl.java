package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.entity.OrderDetails;
import com.itheima.mapper.OrderDetailsMapper;
import com.itheima.service.OrderDetailsService;
import org.springframework.stereotype.Service;


/**
 * @author tang
 * @date 2022/5/14 11:39
 */
@Service
public class OrderDetailsServiceImpl extends ServiceImpl<OrderDetailsMapper,OrderDetails> implements OrderDetailsService {

}
