package com.xxx.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.pay.mapper.OrderInfoMapper;
import com.xxx.pay.service.OrderInfoService;
import generator.entity.OrderInfo;
import org.springframework.stereotype.Service;

/**
* @author tcs
* @description 针对表【t_order_info】的数据库操作Service实现
* @createDate 2022-06-22 23:07:48
*/
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>
implements OrderInfoService {

}
