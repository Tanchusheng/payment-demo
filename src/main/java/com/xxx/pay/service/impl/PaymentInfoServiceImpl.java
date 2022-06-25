package com.xxx.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.xxx.pay.entity.PaymentInfo;
import com.xxx.pay.enums.PayType;
import com.xxx.pay.service.PaymentInfoService;
import com.xxx.pay.mapper.PaymentInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tcs
 * @description 支付日志Service实现
 * @createDate 2022-06-25 22:19:53
 */
@Service
@Slf4j
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo>
        implements PaymentInfoService {

    /**
     * 创建支付日志
     *
     * @param plainText
     */
    @Override
    public void createPaymentInfo(String plainText) {
        log.info("记录支付日志");

        Gson gson = new Gson();
        Map<String, Object> plainTextMap = gson.fromJson(plainText, HashMap.class);
        String orderNo = (String) plainTextMap.get("out_trade_no");
        String transactionId = (String) plainTextMap.get("transaction_id");
        String tradeType = (String) plainTextMap.get("trade_type");
        String tradeState = (String) plainTextMap.get("trade_state");
        Map<String, Object> amount = (Map) plainTextMap.get("amount");
        Integer payerTotal = ((Double) amount.get("payer_total")).intValue();
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderNo(orderNo);
        paymentInfo.setPaymentType(PayType.WXPAY.getType());
        paymentInfo.setTransactionId(transactionId);
        paymentInfo.setTradeType(tradeType);
        paymentInfo.setTradeState(tradeState);
        paymentInfo.setPayerTotal(payerTotal);
        paymentInfo.setContent(plainText);
        baseMapper.insert(paymentInfo);
    }
}
