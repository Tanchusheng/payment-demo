package com.xxx.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.pay.entity.OrderInfo;
import com.xxx.pay.entity.Product;
import com.xxx.pay.enums.OrderStatus;
import com.xxx.pay.mapper.OrderInfoMapper;
import com.xxx.pay.mapper.ProductMapper;
import com.xxx.pay.service.OrderInfoService;
import com.xxx.pay.util.OrderNoUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tcs
 * @description 针对表【t_order_info】的数据库操作Service实现
 * @createDate 2022-06-22 23:07:48
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo>
        implements OrderInfoService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public OrderInfo createOrderByProductId(Long productId) {

        // 查找已存在但未支付的订单
        OrderInfo orderInfo = this.getNoPayOrderByProductId(productId);
        if (orderInfo != null) {
            return orderInfo;
        }

        //获取商品信息
        Product product = productMapper.selectById(productId);

        //生成订单
        orderInfo = new OrderInfo();
        orderInfo.setTitle(product.getTitle());
        orderInfo.setOrderNo(OrderNoUtils.getOrderNo());
        orderInfo.setProductId(productId);
        orderInfo.setTotalFee(product.getPrice());
        orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());
        baseMapper.insert(orderInfo);
        return orderInfo;
    }

    /**
     * 根据商品ID 查询支付订单
     * 防止创建重复订单对象
     *
     * @param productId 商品ID
     * @return
     */
    private OrderInfo getNoPayOrderByProductId(Long productId) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        queryWrapper.eq("order_status", OrderStatus.NOTPAY.getType());
        OrderInfo orderInfo = baseMapper.selectOne(queryWrapper);
        return orderInfo;
    }

    /**
     * 存储订单二维码
     *
     * @param orderNo
     * @param codeUrl
     */
    @Override
    public void saveCodeUrl(String orderNo, String codeUrl) {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCodeUrl(codeUrl);
        baseMapper.update(orderInfo, queryWrapper);
    }

    /**
     * 查询订单列表，并倒序查询
     * @return
     */
    @Override
    public List<OrderInfo> listOrderByCreateTimeDesc() {
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>()
                .orderByDesc("create_time");
        return baseMapper.selectList(queryWrapper);
    }
}
