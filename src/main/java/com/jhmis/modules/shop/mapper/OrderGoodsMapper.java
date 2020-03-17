/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.OrderGoods;
import org.apache.ibatis.annotations.Param;

/**
 * 订单商品MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface OrderGoodsMapper extends BaseMapper<OrderGoods> {
    /**
     * 根据订单ID 删除订单商品（逻辑删除）
     * @param orderId
     * @return
     */
   public  int deleteByLogicOrderId(@Param(value = "orderId") String orderId);
	
}