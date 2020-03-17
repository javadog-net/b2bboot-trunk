/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.customer.entity.QygTOrderDetail;

/**
 * 巨商会订单details非中转表MAPPER接口
 * @author hdx
 * @version 2020-02-12
 */
@MyBatisMapper
public interface QygTOrderDetailMapper extends BaseMapper<QygTOrderDetail> {
	
}