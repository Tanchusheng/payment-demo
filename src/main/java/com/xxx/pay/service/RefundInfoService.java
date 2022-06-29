package com.xxx.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.pay.entity.RefundInfo;

import java.util.List;

/**
* @author tcs
* @description
* @createDate 2022-06-29 21:43:52
*/
public interface RefundInfoService extends IService<RefundInfo> {

    /**
     * 创建退款单记录
     * @param orderNo
     * @param reason
     * @return
     */
    RefundInfo createRefundByOrderNo(String orderNo, String reason);

    /**
     * 更新订单记录
     * @param content
     */
    void updateRefund(String content);

    /**
     * 找出申请退款超过minutes分钟并且未成功的退款单
     * @param minutes
     * @return
     */
    List<RefundInfo> getNoRefundOrderByDuration(int minutes);
}
