package com.xxx.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.pay.entity.PaymentInfo;

/**
* @author tcs
* @description 支付日志Service
* @createDate 2022-06-25 22:19:53
*/
public interface PaymentInfoService extends IService<PaymentInfo> {

    /**
     * 创建支付日志
     * @param plainText
     */
    void createPaymentInfo(String plainText);
}
