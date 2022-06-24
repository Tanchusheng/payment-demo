package com.xxx.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.pay.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author tcs
* @description 针对表【t_order_info】的数据库操作Mapper
* @createDate 2022-06-22 23:07:48
* @Entity generator.entity.OrderInfo
*/
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}
