/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.TrancheProduct;
import com.jhmis.modules.shop.mapper.TrancheProductMapper;

/**
 * 一期商品数据Service
 * @author hdx
 * @version 2018-07-29
 */
@Service
@Transactional(readOnly = true)
public class TrancheProductService extends CrudService<TrancheProductMapper, TrancheProduct> {

	public TrancheProduct get(String id) {
		return super.get(id);
	}
	
	public List<TrancheProduct> findList(TrancheProduct trancheProduct) {
		return super.findList(trancheProduct);
	}
	
	public Page<TrancheProduct> findPage(Page<TrancheProduct> page, TrancheProduct trancheProduct) {
		return super.findPage(page, trancheProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(TrancheProduct trancheProduct) {
		super.save(trancheProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(TrancheProduct trancheProduct) {
		super.delete(trancheProduct);
	}

	@Transactional(readOnly = false)
	public int update(TrancheProduct trancheProduct){
		return mapper.update(trancheProduct);
	}
	
}