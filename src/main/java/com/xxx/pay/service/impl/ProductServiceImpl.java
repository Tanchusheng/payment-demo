package com.xxx.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.pay.entity.Product;
import com.xxx.pay.service.ProductService;
import com.xxx.pay.mapper.ProductMapper;
import org.springframework.stereotype.Service;

/**
* @author tcs
* @description 针对表【t_product】的数据库操作Service实现
* @createDate 2022-06-22 23:20:58
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
implements ProductService{

}
