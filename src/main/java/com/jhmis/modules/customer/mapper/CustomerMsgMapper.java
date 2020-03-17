/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.customer.entity.CustomerMsg;

/**
 * 客单需求MAPPER接口
 * @author hdx
 * @version 2019-04-25
 */
@MyBatisMapper
public interface CustomerMsgMapper extends BaseMapper<CustomerMsg> {
	public void saveS(CustomerMsg customerMsg);
	//根据msgId获取项目信息
	public CustomerMsg getByMsgid(String msgId);
	//根据hps提供的项目编码（P码）获取项目信息
	public CustomerMsg getByProjectId(String projectId);
	
}