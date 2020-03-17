/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.OrderLog;

/**
 * 订单日志MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface OrderLogMapper extends BaseMapper<OrderLog> {
	
}