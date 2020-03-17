/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.purchasermainrel.service;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.purchasermainrel.entity.PurchaserMasterSlaveRel;
import com.jhmis.modules.purchasermainrel.mapper.PurchaserMasterSlaveRelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采购商主副关系Service
 * @author wbn
 * @version 2019-07-24
 */
@Service
@Transactional(readOnly = true)
public class PurchaserMasterSlaveRelService extends CrudService<PurchaserMasterSlaveRelMapper, PurchaserMasterSlaveRel> {
	@Autowired
	PurchaserMasterSlaveRelMapper purchaserMasterSlaveRelMapper;
	public PurchaserMasterSlaveRel get(String id) {
		return super.get(id);
	}
	
	public List<PurchaserMasterSlaveRel> findList(PurchaserMasterSlaveRel purchaserMasterSlaveRel) {
		return super.findList(purchaserMasterSlaveRel);
	}
	
	public Page<PurchaserMasterSlaveRel> findPage(Page<PurchaserMasterSlaveRel> page, PurchaserMasterSlaveRel purchaserMasterSlaveRel) {
		return super.findPage(page, purchaserMasterSlaveRel);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaserMasterSlaveRel purchaserMasterSlaveRel) {
		super.save(purchaserMasterSlaveRel);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserMasterSlaveRel purchaserMasterSlaveRel) {
		super.delete(purchaserMasterSlaveRel);
	}
	
	/**
	 * 根据采购商和商品SKu删除关联关系
	 * @param purchaserId
	 */
	@Transactional(readOnly = false)
	public void delete(String purchaserId,String purchaserSlaveId){
		PurchaserMasterSlaveRel rel=new PurchaserMasterSlaveRel();
		rel.setPurchaserMasterId(purchaserId);
		rel.setPurchaserSlaveId(purchaserSlaveId);
		purchaserMasterSlaveRelMapper.deletePurchaserRel(rel);
	};
	
	/**
	 * 根据子账号获取关联信息
	 * @param purchaserSlaveId
	 */
	@Transactional(readOnly = false)
	public PurchaserMasterSlaveRel getMasterPurchaser(String purchaserSlaveId){
		return purchaserMasterSlaveRelMapper.getMasterPurchaser(purchaserSlaveId);
	};

	/**
	 * 获取子账号数据
	 * @param purchaserMasterId
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<PurchaserMasterSlaveRel> getSubByAdmin(String purchaserMasterId){
		return purchaserMasterSlaveRelMapper.getSubByAdmin(purchaserMasterId);
	};

}