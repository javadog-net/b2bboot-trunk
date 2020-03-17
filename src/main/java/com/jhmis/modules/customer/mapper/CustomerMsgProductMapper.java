/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.customer.entity.CustomerMsgProduct;

/**
 * 客单产品关联表MAPPER接口
 * @author hdx
 * @version 2019-04-16
 */
@MyBatisMapper
public interface CustomerMsgProductMapper extends BaseMapper<CustomerMsgProduct> {
	public void deleteByMsgid(String customerMsgId);
}