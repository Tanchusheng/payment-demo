package com.xxx.pay.controller;

import com.xxx.pay.service.WxPayService;
import com.xxx.pay.web.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    /**
     * native 下单
     * @param productId
     * @return
     * @throws Exception
     */
    @ApiOperation("调用统一下单API，生成支付二维码")
    @PostMapping("/native/{productId}")
    public R nativePay(@PathVariable Long productId) throws Exception{
        log.info("发起支付请求");
        Map<String, Object> map = wxPayService.nativePay(productId);
        return R.ok().setData(map);
    }

}
