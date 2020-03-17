/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.StorePriceGroup;
import com.jhmis.modules.shop.mapper.StorePriceGroupMapper;

/**
 * 商品价格分组Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class StorePriceGroupService extends CrudService<StorePriceGroupMapper, StorePriceGroup> {

	public StorePriceGroup get(String id) {
		return super.get(id);
	}
	
	public List<StorePriceGroup> findList(StorePriceGroup storePriceGroup) {
		return super.findList(storePriceGroup);
	}
	
	public Page<StorePriceGroup> findPage(Page<StorePriceGroup> page, StorePriceGroup storePriceGroup) {
		return super.findPage(page, storePriceGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(StorePriceGroup storePriceGroup) {
		super.save(storePriceGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(StorePriceGroup storePriceGroup) {
		super.delete(storePriceGroup);
	}

	@Transactional(readOnly = false)
	public void deleteByStoreIdOrCode(String storeId,String code){
		mapper.deleteByStoreIdOrCode(storeId,code);
	}
}