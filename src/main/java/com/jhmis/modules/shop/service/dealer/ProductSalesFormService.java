/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service.dealer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.dealer.ProductSalesForm;
import com.jhmis.modules.shop.mapper.dealer.ProductSalesFormMapper;

/**
 * 销售样表Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class ProductSalesFormService extends CrudService<ProductSalesFormMapper, ProductSalesForm> {

	public ProductSalesForm get(String id) {
		return super.get(id);
	}
	
	public List<ProductSalesForm> findList(ProductSalesForm productSalesForm) {
		return super.findList(productSalesForm);
	}
	
	public Page<ProductSalesForm> findPage(Page<ProductSalesForm> page, ProductSalesForm productSalesForm) {
		return super.findPage(page, productSalesForm);
	}
	
	@Transactional(readOnly = false)
	public void save(ProductSalesForm productSalesForm) {
		super.save(productSalesForm);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProductSalesForm productSalesForm) {
		super.delete(productSalesForm);
	}
	
}