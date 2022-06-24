package com.xxx.pay.service.impl;

import com.google.gson.Gson;
import com.xxx.pay.config.WxPayConfig;
import com.xxx.pay.entity.OrderInfo;
import com.xxx.pay.enums.OrderStatus;
import com.xxx.pay.enums.wxpay.WxApiType;
import com.xxx.pay.enums.wxpay.WxNotifyType;
import com.xxx.pay.service.OrderInfoService;
import com.xxx.pay.service.WxPayService;
import com.xxx.pay.util.OrderNoUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 微信支付Service实现层
 * @author: tcs
 * @date: 2022/6/24 9:47
 * @version: 1.0
 */
@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {

    @Resource
    private WxPayConfig wxPayConfig;

    @Resource
    private CloseableHttpClient wxPayClient;

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 创建订单，调用Native支付接口
     *
     * @param productId
     * @return code_url 和 订单号
     * @throws Exception
     */
    @Override
    public Map<String, Object> nativePay(Long productId) throws Exception {
        log.info("生成订单");

        // 生成订单

        OrderInfo orderInfo = orderInfoService.createOrderByProductId(productId);
        String codeUrl = orderInfo.getCodeUrl();
        if(orderInfo != null && !StringUtils.isEmpty(codeUrl)){
            log.info("订单已存在，二维码已保存");
            //返回二维码
            Map<String, Object> map = new HashMap<>();
            map.put("codeUrl", codeUrl);
            map.put("orderNo", orderInfo.getOrderNo());
            return map;
        }
        log.info("调用统一下单API");

        // 调用统一下单API
        HttpPost httpPost = new HttpPost(wxPayConfig.getDomain().concat(WxApiType.NATIVE_PAY.getType()));

        // 请求body参数
        Gson gson = new Gson();
        Map paramsMap = new HashMap();
        paramsMap.put("appid", wxPayConfig.getAppid());
        paramsMap.put("mchid", wxPayConfig.getMchId());
        paramsMap.put("description", orderInfo.getTitle());
        paramsMap.put("out_trade_no", orderInfo.getOrderNo());
        paramsMap.put("notify_url", wxPayConfig.getNotifyDomain().concat(WxNotifyType.NATIVE_NOTIFY.getType()));
        Map amountMap = new HashMap();
        amountMap.put("total", orderInfo.getTotalFee());
        amountMap.put("currency", "CNY");
        paramsMap.put("amount", amountMap);
        //将参数转换成json字符串
        String jsonParams = gson.toJson(paramsMap);
        log.info("请求参数：" + jsonParams);

        StringEntity entity = new StringEntity(jsonParams, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");

        // 完成签名并执行请求
        CloseableHttpResponse response = wxPayClient.execute(httpPost);
        try {

            String bodyString = EntityUtils.toString(response.getEntity()); // 响应体
            int statusCode = response.getStatusLine().getStatusCode(); // 状态码
            if (statusCode == 200) { // 处理成功
                log.info("成功, 返回结果 = " + bodyString);
            } else if (statusCode == 204) { //处理成功，无返回Body
                log.info("成功");
            } else {
                log.info("Native下单失败,响应码 = " + statusCode + ",返回结果 = " + bodyString);
                throw new IOException("request failed");
            }

            //响应结果
            Map<String, String> resultMap = gson.fromJson(bodyString, HashMap.class);

            Map<String, Object> map = new HashMap<>();
            map.put("codeUrl", resultMap.get("code_url"));
            map.put("orderNo", orderInfo.getOrderNo());
            return map;
        } finally {
            response.close();
        }

    }
}
