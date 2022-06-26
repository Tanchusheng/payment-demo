package com.xxx.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.pay.entity.OrderInfo;
import com.xxx.pay.enums.OrderStatus;

import java.util.List;

/**
* @author tcs
* @description 针对表【t_order_info】的数据库操作Service
* @createDate 2022-06-22 23:07:48
*/
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 根据商品信息创建订单
     * @param productId
     * @return
     */
    OrderInfo createOrderByProductId(Long productId);

    /**
     * 保存二维码
     * @param orderNo
     * @param codeUrl
     */
    void saveCodeUrl(String orderNo,String codeUrl);

    /**
     * 查询订单列表
     * @return
     */
    List<OrderInfo> listOrderByCreateTimeDesc();

    /**
     * 更新订单状态
     * @param orderNo
     * @param orderStatus
     */
    void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus);

    /**
     * 根据订单号获取订单状态
     * @param orderNo
     * @return
     */
    String getOrderStatus(String orderNo);

    /**
     * 找出创建超过minutes分钟并且未支付的订单
     * @param minutes
     * @return
     */
    List<OrderInfo> getNoPayOrderByDuration(int minutes);
}
