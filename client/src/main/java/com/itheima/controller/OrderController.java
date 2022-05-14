package com.itheima.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.common.R;
import com.itheima.dto.OrderSubmitDto;
import com.itheima.entity.AddressBook;
import com.itheima.entity.Employee;
import com.itheima.entity.Order;
import com.itheima.mapper.OrderMapper;
import com.itheima.service.AddressBookService;
import com.itheima.service.OrderService;
import com.itheima.vo.GetEmployeeByPageVo;
import com.itheima.vo.GetOrderByPageVo;
import com.itheima.vo.OrderSubmitVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tang
 * @date 2022/5/13 14:14
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 提交订单
     *
     * @param dto 传入参数
     * @return success下单成功
     */
    @PostMapping("/submit")
    public R<Object> orderSubmit(@RequestBody OrderSubmitDto dto) {
        if (ObjectUtil.isEmpty(dto)) {
            return R.error("参数为空");
        }
        Order order = new Order();
        BeanUtils.copyProperties(dto, order);
        order.setRemark(dto.getRemark());
        order.setPayMethod(dto.getPayMethod());
        order.setAddressBookId(dto.getAddressBookId());
        orderService.submit(order);
        return R.success("下单成功");
    }

    /**
     * 订单分页查询
     *
     * @param page     页码
     * @param pageSize 每页条数
     * @return success 订单页面  error 订单为空
     */
    @GetMapping("/userPage")
    public R<Object> selectByPage(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {

        GetOrderByPageVo orderByPage = orderService.getOrderByPage(page, pageSize);

        if (ObjectUtil.isEmpty(orderByPage)) {
            return R.error("订单为空");
        }
        return R.success(orderByPage);
    }


}
