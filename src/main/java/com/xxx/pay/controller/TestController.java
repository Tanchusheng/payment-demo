package com.xxx.pay.controller;

import com.xxx.pay.config.WxPayConfig;
import com.xxx.pay.web.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description:
 * @author: tcs
 * @date: 2022/6/23 22:43
 * @version: 1.0
 **/

@Api(tags = "测试控制器")
@RequestMapping("/api/test")
@RestController
public class TestController {

    @Resource
    private WxPayConfig wxPayConfig;

    @GetMapping("/get-wx-pay-config")
    public R getWxPayConfig(){
        String mchId = wxPayConfig.getMchId();
        return R.ok().data("mchId",mchId);
    }
}
