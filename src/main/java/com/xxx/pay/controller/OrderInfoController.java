package com.xxx.pay.controller;

import com.xxx.pay.entity.OrderInfo;
import com.xxx.pay.enums.OrderStatus;
import com.xxx.pay.service.OrderInfoService;
import com.xxx.pay.web.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 订单控制层
 * @author: tcs
 * @date: 2022/6/24 13:29
 * @version: 1.0
 */

@CrossOrigin
@Api(tags = "商品订单管理")
@RestController
@RequestMapping("/api/order-info")
public class OrderInfoController {

    @Resource
    private OrderInfoService orderInfoService;

    @ApiOperation("订单列表")
    @GetMapping("/list")
    public R list() {
        List<OrderInfo> list = orderInfoService.listOrderByCreateTimeDesc();
        return R.ok().data("list", list);
    }

    /**
     * 查询本地订单状态
     *
     * @param orderNo
     * @return
     */
    @ApiOperation("查询本地订单状态")
    @GetMapping("/query-order-status/{orderNo}")
    public R queryOrderStatus(@PathVariable String orderNo) {
        String orderStatus = orderInfoService.getOrderStatus(orderNo);
        if (OrderStatus.SUCCESS.getType().equals(orderStatus)) {
            //支付成功
            return R.ok();
        }
        return R.ok().setCode(101).setMessage("支付中...");
    }
}
