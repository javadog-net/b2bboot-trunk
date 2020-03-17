/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper.purchaser;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAddress;

/**
 * 采购商地址管理MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface PurchaserAddressMapper extends BaseMapper<PurchaserAddress> {
	public void clearDefault(String purchaserId);

	public void setDefault(String id);
}