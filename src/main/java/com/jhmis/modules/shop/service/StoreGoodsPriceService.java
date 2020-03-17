/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.StoreGoodsPrice;
import com.jhmis.modules.shop.mapper.StoreGoodsPriceMapper;

/**
 * 店铺商品价格Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class StoreGoodsPriceService extends CrudService<StoreGoodsPriceMapper, StoreGoodsPrice> {

	public StoreGoodsPrice get(String id) {
		return super.get(id);
	}
	
	public List<StoreGoodsPrice> findList(StoreGoodsPrice storeGoodsPrice) {
		return super.findList(storeGoodsPrice);
	}
	
	public Page<StoreGoodsPrice> findPage(Page<StoreGoodsPrice> page, StoreGoodsPrice storeGoodsPrice) {
		return super.findPage(page, storeGoodsPrice);
	}
	
	@Transactional(readOnly = false)
	public void save(StoreGoodsPrice storeGoodsPrice) {
		super.save(storeGoodsPrice);
	}
	
	@Transactional(readOnly = false)
	public void delete(StoreGoodsPrice storeGoodsPrice) {
		super.delete(storeGoodsPrice);
	}

	@Transactional(readOnly = false)
	public void deleteBySPGId(String storePriceGroupId){
		mapper.deleteBySPGId(storePriceGroupId);
	}
}