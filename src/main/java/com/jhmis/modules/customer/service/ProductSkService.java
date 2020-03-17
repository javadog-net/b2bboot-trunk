/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.ProductSk;
import com.jhmis.modules.customer.mapper.ProductSkMapper;

/**
 * 商空系列数据Service
 * @author mll
 * @version 2019-08-14
 */
@Service
@Transactional(readOnly = true)
public class ProductSkService extends CrudService<ProductSkMapper, ProductSk> {

	public ProductSk get(String id) {
		return super.get(id);
	}
	
	public List<ProductSk> findList(ProductSk productSk) {
		return super.findList(productSk);
	}
	
	public Page<ProductSk> findPage(Page<ProductSk> page, ProductSk productSk) {
		return super.findPage(page, productSk);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductSk productSk) {
		super.save(productSk);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductSk productSk) {
		super.delete(productSk);
	}
	
}