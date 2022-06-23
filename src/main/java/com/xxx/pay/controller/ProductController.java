package com.xxx.pay.controller;

import com.xxx.pay.entity.Product;
import com.xxx.pay.service.OrderInfoService;
import com.xxx.pay.service.ProductService;
import com.xxx.pay.web.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: TODO
 * @author: tcs
 * @date: 2022/6/22 21:54
 * @version: 1.0
 **/

@RestController
@RequestMapping("/api/product")
@CrossOrigin // 跨域
@Api(tags = "商品管理")
public class ProductController {

    @Resource
    private ProductService productService;



    @ApiOperation(value = "测试查询")
    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @ApiOperation(value = "商品列表")
    @GetMapping("/list")
    public R list(){
        List<Product> list = productService.list();
        return R.ok().data("productList",list);
    }
}
