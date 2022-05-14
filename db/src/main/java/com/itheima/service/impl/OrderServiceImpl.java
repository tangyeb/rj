package com.itheima.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.common.CustomException;
import com.itheima.common.TokenHolder;
import com.itheima.entity.*;
import com.itheima.mapper.OrderMapper;
import com.itheima.service.*;
import com.itheima.vo.GetEmployeeByPageVo;
import com.itheima.vo.GetOrderByPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tang
 * @date 2022/5/11 14:19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private OrderDetailsService orderDetailsService;

    @Override
    public GetOrderByPageVo selectAll(String beginTime,
                                      String endTime,
                                      String number,
                                      Integer page,
                                      Integer pageSize) {
        number = "%" + number + "%";
        Integer i = (page - 1) * pageSize;
        List<Order> orders = orderMapper.selectAll(beginTime, endTime, number, i, pageSize);
        GetOrderByPageVo getOrderByPageVo = new GetOrderByPageVo();
        getOrderByPageVo.setRecords(orders);
        getOrderByPageVo.setTotal(orderMapper.selectCount());
        getOrderByPageVo.setSize(pageSize);
        getOrderByPageVo.setCurrent(page);
        return getOrderByPageVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(Order order) {
        String userId = TokenHolder.getCurrentId();
        //查询购物车数据
        LambdaQueryWrapper<ShoppingCart> qw = new LambdaQueryWrapper<>();
        qw.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(qw);
        if (CollUtil.isEmpty(shoppingCarts)) {
            throw new CustomException("购物车为空，不能下单");
        }

        User user = userService.getById(userId);
        String addressBookId = order.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null) {
            throw new CustomException("地址信息有误，不能下单");
        }

        String orderId = IdUtil.getSnowflakeNextIdStr();
        //添加订单
        //添加订单详细
        List<OrderDetails> orderDetailsList = shoppingCarts.stream().map((item) -> {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderId(orderId);
            orderDetails.setName(item.getName());
            orderDetails.setNumber(item.getNumber());
            orderDetails.setId(IdUtil.getSnowflakeNextIdStr());
            orderDetails.setSetmealId(item.getSetmealId());
            orderDetails.setDishId(item.getDishId());
            orderDetails.setDishFlavor(item.getDishFlavor());
            orderDetails.setAmount(item.getAmount());
            orderDetails.setMoney(item.getAmount() * item.getNumber());
            orderDetails.setImage(item.getImage());
            return orderDetails;
        }).collect(Collectors.toList());
        //添加订单详细表
        orderDetailsService.saveBatch(orderDetailsList);
        //计算每件商品总价
        double sum = orderDetailsList.stream().mapToDouble(OrderDetails::getMoney).sum();
        //补全订单信息并添加
        order.setId(orderId);
        order.setOrderTime(DateTime.now());
        order.setCheckoutTime(DateTime.now());
        order.setStatus(2);
        order.setAmount(sum);
        order.setUserId(userId);
        order.setNumber(orderId);
        order.setUserName((user.getName()==null)?user.getId():user.getName());
        order.setConsignee(addressBook.getConsignee());
        order.setPhone(addressBook.getPhone());
        order.setAddress(addressBook.getDetail());
        this.save(order);
        shoppingCartService.remove(qw);

    }

    @Override
    public GetOrderByPageVo getOrderByPage(Integer page, Integer pageSize) {
        IPage<Order> p = new Page<>(page, pageSize);
        //2 执行分页查询
        orderMapper.selectPage(p,null);
        GetOrderByPageVo getOrderByPageVo = new GetOrderByPageVo();
        getOrderByPageVo.setRecords(p.getRecords());
        getOrderByPageVo.setTotal(p.getTotal());
        getOrderByPageVo.setSize(pageSize);
        getOrderByPageVo.setCurrent(p.getCurrent());
        getOrderByPageVo.setOrders(p.orders());
        getOrderByPageVo.setOptimizeCountSql(true);
        getOrderByPageVo.setHitCount(false);
        getOrderByPageVo.setCountId(null);
        getOrderByPageVo.setMaxLimit(null);
        getOrderByPageVo.setSearchCount(true);
        getOrderByPageVo.setPages(p.getPages());
        return getOrderByPageVo;
    }

}
