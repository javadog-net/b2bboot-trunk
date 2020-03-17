/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.purchasergoodsrel.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.purchasergoodsrel.entity.PurchaserGoodsRel;

/**
 * 采购商可采商品MAPPER接口
 * @author wangbn
 * @version 2019-07-24
 */
@MyBatisMapper
public interface PurchaserGoodsRelMapper extends BaseMapper<PurchaserGoodsRel> {
	
	/**
	 * 删除
	 * @param purchaserId
	 * @param goodsSku
	 */
	public void deletePurchaserGoodsRel(PurchaserGoodsRel rel);
	public void updatePrice(PurchaserGoodsRel purchaserId);
	
	
	
}