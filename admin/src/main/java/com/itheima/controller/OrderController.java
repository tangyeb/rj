package com.itheima.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.common.R;
import com.itheima.dto.StatusDto;
import com.itheima.entity.Order;
import com.itheima.service.OrderService;
import com.itheima.vo.GetOrderByPageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tang
 * @date 2022/5/11 15:21
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 分页查询
     *
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @param number    订单号
     * @param page      起始页码
     * @param pageSize  页码条数
     * @return 分页查询结果
     */
    @GetMapping("/page")
    public R<Object> selectByPage(@RequestParam(value = "beginTime", required = false) String beginTime,
                                  @RequestParam(value = "endTime", required = false) String endTime,
                                  @RequestParam(value = "number", required = false, defaultValue = "") String number,
                                  @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                  @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        GetOrderByPageVo getOrderByPageVo = orderService.selectAll(beginTime, endTime, number, page, pageSize);
        return R.success(getOrderByPageVo);

    }

    /**
     * 修改订单状态
     *
     * @param dto 传入参数 status id
     * @return success 更新成功 error 参数为空
     */
    @PutMapping("")
    public R<String> status(@RequestBody StatusDto dto) {
        if (ObjectUtil.isEmpty(dto)) {
            return R.error("参数为空");
        }
        Order order = new Order();
        order.setStatus(dto.getStatus());
        order.setId(dto.getId());
        orderService.updateById(order);
        return R.success("更新成功");
    }


}
