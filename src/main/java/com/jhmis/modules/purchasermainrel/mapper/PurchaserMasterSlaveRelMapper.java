/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.purchasermainrel.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.purchasermainrel.entity.PurchaserMasterSlaveRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采购商主副关系MAPPER接口
 * @author wbn
 * @version 2019-07-24
 */
@MyBatisMapper
public interface PurchaserMasterSlaveRelMapper extends BaseMapper<PurchaserMasterSlaveRel> {
	
	/**
	 * 删除关联关系
	 * @param rel
	 */
	public void deletePurchaserRel(PurchaserMasterSlaveRel rel);
	
	
	public PurchaserMasterSlaveRel getMasterPurchaser(@Param("slaveId") String slaveId);

	/**
	 * 获取子账号数据
	 * @param purchaserMasterId
	 * @return
	 */
	public List<PurchaserMasterSlaveRel> getSubByAdmin(@Param("purchaserMasterId") String purchaserMasterId);
	
}