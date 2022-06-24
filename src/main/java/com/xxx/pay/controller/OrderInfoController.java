package com.xxx.pay.controller;

import com.xxx.pay.entity.OrderInfo;
import com.xxx.pay.service.OrderInfoService;
import com.xxx.pay.web.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public R list(){
        List<OrderInfo> list = orderInfoService.listOrderByCreateTimeDesc();
        return R.ok().data("list",list);
    }
}
