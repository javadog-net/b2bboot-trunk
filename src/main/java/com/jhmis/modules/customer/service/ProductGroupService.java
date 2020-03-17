/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.ProductGroup;
import com.jhmis.modules.customer.mapper.ProductGroupMapper;

/**
 * 产品组编码表Service
 * @author hdx
 * @version 2019-04-17
 */
@Service
@Transactional(readOnly = true)
public class ProductGroupService extends CrudService<ProductGroupMapper, ProductGroup> {

	public ProductGroup get(String id) {
		return super.get(id);
	}
	
	public List<ProductGroup> findList(ProductGroup productGroup) {
		return super.findList(productGroup);
	}
	
	public Page<ProductGroup> findPage(Page<ProductGroup> page, ProductGroup productGroup) {
		return super.findPage(page, productGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductGroup productGroup) {
		super.save(productGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductGroup productGroup) {
		super.delete(productGroup);
	}
	
}