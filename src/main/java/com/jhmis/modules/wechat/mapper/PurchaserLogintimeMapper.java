/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.PurchaserLogintime;

/**
 * 采购商登录统计MAPPER接口
 * @author a
 * @version 2019-11-29
 */
@MyBatisMapper
public interface PurchaserLogintimeMapper extends BaseMapper<PurchaserLogintime> {
	public Integer findNumByAccountId(String accountId);
}