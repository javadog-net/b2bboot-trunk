/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.Brand;
import com.jhmis.modules.shop.mapper.BrandMapper;

/**
 * 品牌管理Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class BrandService extends CrudService<BrandMapper, Brand> {

	public Brand get(String id) {
		return super.get(id);
	}
	
	public List<Brand> findList(Brand brand) {
		return super.findList(brand);
	}
	
	public Page<Brand> findPage(Page<Brand> page, Brand brand) {
		return super.findPage(page, brand);
	}
	
	@Transactional(readOnly = false)
	public void save(Brand brand) {
		super.save(brand);
	}
	
	@Transactional(readOnly = false)
	public void delete(Brand brand) {
		super.delete(brand);
	}

	@Transactional(readOnly = false)
	public List<Brand> selectBrandForSearch(String key){
		return mapper.selectBrandForSearch(key);
	}
	
}