/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.OrderPay;
import com.jhmis.modules.shop.entity.Orders;

import java.util.List;

/**
 * 订单支付MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface OrderPayMapper extends BaseMapper<OrderPay> {
    public  int getOrderPayCount(OrderPay orderPay);


    public List<OrderPay>findOrdersPayList(OrderPay orderPay);
}