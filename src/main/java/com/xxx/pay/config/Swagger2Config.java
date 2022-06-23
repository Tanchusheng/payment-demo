package com.xxx.pay.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description: swagger2 配置
 * @author: tcs
 * @date: 2022/6/22 21:57
 * @version: 1.0
 **/

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config {

    /**
     * 接口地址：http://localhost:8080/doc.html
     * @return
     */

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(
                new ApiInfoBuilder()
                        .title("微信支付案例接口文档")
                        .description("这是一个微信支付案例的接口文档")
                        .version("1.0")
                        .contact(new Contact("tan","","746261725@qq.com"))
                        .build());
    }


}
