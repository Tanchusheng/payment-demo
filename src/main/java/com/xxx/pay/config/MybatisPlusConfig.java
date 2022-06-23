package com.xxx.pay.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @description: MybatisPlusConfig
 * @author: tcs
 * @date: 2022/6/22 22:49
 * @version: 1.0
 **/

@Configuration
@MapperScan("com.xxx.pay.mapper") // 持久层扫描
@EnableTransactionManagement // 启动事务管理
public class MybatisPlusConfig {
}
