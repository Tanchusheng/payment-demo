package com.xxx.pay.controller;

import com.google.gson.Gson;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.xxx.pay.service.WxPayService;
import com.xxx.pay.util.HttpUtils;
import com.xxx.pay.util.WechatPay2ValidatorForRequest;
import com.xxx.pay.web.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: tcs
 * @date: 2022/6/24 9:45
 * @version: 1.0
 */

@CrossOrigin
@RestController
@RequestMapping("/api/wx-pay")
@Api(tags = "网站微信支付")
@Slf4j
public class WxPayController {


    @Resource
    private WxPayService wxPayService;

    @Resource
    private Verifier verifier;

    /**
     * native 下单
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @ApiOperation("调用统一下单API，生成支付二维码")
    @PostMapping("/native/{productId}")
    public R nativePay(@PathVariable Long productId) throws Exception {
        log.info("发起支付请求");
        Map<String, Object> map = wxPayService.nativePay(productId);
        return R.ok().setData(map);
    }

    /**
     * native 支付通知
     * 微信支付通过支付通知接口将用户支付成功消息通知给商户
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation("支付通知")
    @PostMapping("/native/notify")
    public String nativeNotify(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();//应答对象
        try {
            //处理通知参数
            String body = HttpUtils.readData(request);
            Map<String, Object> bodyMap = gson.fromJson(body, HashMap.class);
            log.info("支付通知的id ===> {}", bodyMap.get("id"));
            log.info("支付通知的完整数据 ===> {}", body);

            //TODO : 签名的验证
            WechatPay2ValidatorForRequest validator = new WechatPay2ValidatorForRequest(verifier, body, request.getRequestedSessionId());
            if (!validator.validate(request)) {
                log.error("通知验签失败");
                response.setStatus(500);
                map.put("code", "ERROR");
                map.put("message", "通知验签失败");
                return gson.toJson(map);
            }
            log.info("通知验签成功");
            wxPayService.processOrder(bodyMap);
            //TODO : 处理订单
            //成功应答：成功应答必须为200或204，否则就是失败应答
            response.setStatus(200);
            map.put("code", "SUCCESS");
            map.put("message", "成功");
            return gson.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            // 测试错误应答
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message", "系统错误");
            return gson.toJson(map);
        }
    }

    /**
     * 用户取消订单
     *
     * @param orderNo
     * @return
     * @throws Exception
     */
    @ApiOperation("用户取消订单")
    @PostMapping("/cancel/{orderNo}")
    public R cancel(@PathVariable String orderNo) throws Exception {
        log.info("取消订单");
        wxPayService.cancelOrder(orderNo);
        return R.ok().setMessage("订单已取消");
    }

    /**
     * 查询订单
     * @param orderNo
     * @return
     * @throws Exception
     */
    @ApiOperation("查询订单：测试订单状态用")
    @GetMapping("query/{orderNo}")
    public R queryOrder(@PathVariable String orderNo) throws Exception {
        log.info("查询订单");
        String result = wxPayService.queryOrder(orderNo);
        return R.ok().setMessage("查询成功").data("result", result);
    }

}
