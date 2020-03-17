/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.purchasergoodsrel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.common.utils.DateUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.purchasergoodsrel.entity.PurchaserGoodsRel;
import com.jhmis.modules.purchasergoodsrel.mapper.PurchaserGoodsRelMapper;

/**
 * 采购商可采商品Service
 * @author wangbn
 * @version 2019-07-24
 */
@Service
@Transactional(readOnly = true)
public class PurchaserGoodsRelService extends CrudService<PurchaserGoodsRelMapper, PurchaserGoodsRel> {
	
	@Autowired
	PurchaserGoodsRelMapper  purchaserGoodsRelMapper;

	public PurchaserGoodsRel get(String id) {
		return super.get(id);
	}
	
	public List<PurchaserGoodsRel> findList(PurchaserGoodsRel purchaserGoodsRel) {
		return super.findList(purchaserGoodsRel);
	}
	
	public Page<PurchaserGoodsRel> findPage(Page<PurchaserGoodsRel> page, PurchaserGoodsRel purchaserGoodsRel) {
		return super.findPage(page, purchaserGoodsRel);
	}
	
	@Transactional(readOnly = false)
	public void save(PurchaserGoodsRel purchaserGoodsRel) {
		super.save(purchaserGoodsRel);
	}
	
	@Transactional(readOnly = false)
	public void updatePrice(PurchaserGoodsRel purchaserGoodsRel) {
		System.out.println("======sku==========="+purchaserGoodsRel.getGoodsSku());
		System.out.println("======purchaser==========="+purchaserGoodsRel.getPurchaserId());
		System.out.println("======price==========="+purchaserGoodsRel.getExclusivePrice());
		purchaserGoodsRel.setState("1");
		purchaserGoodsRel.setOpeDate(DateUtils.getDateTime());
		purchaserGoodsRelMapper.updatePrice(purchaserGoodsRel);
	}
	
	@Transactional(readOnly = false)
	public void delete(PurchaserGoodsRel purchaserGoodsRel) {
		super.delete(purchaserGoodsRel);
	}
	
	/**
	 * 根据采购商和商品SKu删除关联关系
	 * @param purchaserId
	 * @param goodsSku
	 */
	@Transactional(readOnly = false)
	public void delete(String purchaserId,String goodsSku){
		System.out.println("purchaserId============================"+purchaserId);
		System.out.println("goodsSku============================"+goodsSku);
		PurchaserGoodsRel rel=new PurchaserGoodsRel();
		rel.setPurchaserId(purchaserId);
		rel.setGoodsSku(goodsSku);
		purchaserGoodsRelMapper.deletePurchaserGoodsRel(rel);
	};
	
}