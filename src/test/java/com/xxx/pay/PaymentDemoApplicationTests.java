package com.xxx.pay;

import com.xxx.pay.config.WxPayConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.PrivateKey;

@SpringBootTest
class PaymentDemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Resource
	private WxPayConfig wxPayConfig;

	/**
	 * 获取商户私钥
	 */
	@Test
	public void testGetPrivateKey(){

		// 获取私钥路径
		String privateKeyPath = wxPayConfig.getPrivateKeyPath();

		// 获取商户私钥
		PrivateKey privateKey = wxPayConfig.getPrivateKey(privateKeyPath);

		System.out.println(privateKey);

	}

}
