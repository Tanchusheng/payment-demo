package com.xxx.pay.service;

import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * @description:
 * @author: tcs
 * @date: 2022/6/24 9:47
 * @version: 1.0
 */

public interface WxPayService {

    /**
     * 创建订单，调用Native支付接口
     * @param productId
     * @return
     * @throws Exception
     */
    Map<String, Object> nativePay(Long productId) throws Exception;

    /**
     * 处理订单
     * @param bodyMap
     */
    void processOrder(Map<String, Object> bodyMap) throws GeneralSecurityException;

    void cancelOrder(String orderNo) throws Exception;

    String queryOrder(String orderNo) throws Exception;
}
