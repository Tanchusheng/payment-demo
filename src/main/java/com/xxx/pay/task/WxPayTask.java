package com.xxx.pay.task;

import com.xxx.pay.entity.OrderInfo;
import com.xxx.pay.entity.RefundInfo;
import com.xxx.pay.service.OrderInfoService;
import com.xxx.pay.service.RefundInfoService;
import com.xxx.pay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 微信支付定时任务
 * @author: tcs
 * @date: 2022/6/25 22:49
 * @version: 1.0
 **/
@Component
@Slf4j
public class WxPayTask {

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private WxPayService wxPayService;

    @Resource
    private RefundInfoService refundInfoService;

    /**
     * 测试
     * (cron="秒 分 时 日 月 周")
     * *:每隔一秒执行
     * 0/3：从第0秒开始，每隔3秒执行一次
     * 1-3: 从第1秒开始执行，到第3秒结束执行
     * 1,2,3：第1、2、3秒执行
     * ?：不指定，若指定日期，则不指定周，反之同理
     */
//    @Scheduled(cron = "0/3 * * * * ?")
    public void task1() {
        log.info("task1 执行");
    }

    /**
     * 从第0秒开始每隔30秒执行1次，查询创建超过5分钟，并且未支付的订单
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void orderConfirm() throws Exception {
        log.info("orderConfirm 被执行......");
        List<OrderInfo> orderInfoList = orderInfoService.getNoPayOrderByDuration(5);
        for (OrderInfo orderInfo : orderInfoList) {
            String orderNo = orderInfo.getOrderNo();
            log.warn("超时订单 ===> {}", orderNo);
            //核实订单状态：调用微信支付查单接口
            wxPayService.checkOrderStatus(orderNo);
        }
    }

    public void refundConfirm() throws Exception {
        log.info("refundConfirm 被执行......");
        //找出申请退款超过5分钟并且未成功的退款单
        List<RefundInfo> refundInfoList = refundInfoService.getNoRefundOrderByDuration(5);
        for (RefundInfo refundInfo : refundInfoList) {
            String refundNo = refundInfo.getRefundNo();
            log.warn("超时未退款的退款单号 ===> {}", refundNo);
            //核实订单状态：调用微信支付查询退款接口
            wxPayService.checkRefundStatus(refundNo); } }
    }
