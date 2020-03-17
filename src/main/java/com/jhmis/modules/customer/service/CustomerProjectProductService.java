/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.CustomerProjectProduct;
import com.jhmis.modules.customer.mapper.CustomerProjectProductMapper;

/**
 * 客单漏斗项目产品Service
 * @author hdx
 * @version 2019-04-16
 */
@Service
@Transactional(readOnly = true)
public class CustomerProjectProductService extends CrudService<CustomerProjectProductMapper, CustomerProjectProduct> {

	public CustomerProjectProduct get(String id) {
		return super.get(id);
	}
	
	public List<CustomerProjectProduct> findList(CustomerProjectProduct customerProjectProduct) {
		return super.findList(customerProjectProduct);
	}
	
	public Page<CustomerProjectProduct> findPage(Page<CustomerProjectProduct> page, CustomerProjectProduct customerProjectProduct) {
		return super.findPage(page, customerProjectProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerProjectProduct customerProjectProduct) {
		super.save(customerProjectProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerProjectProduct customerProjectProduct) {
		super.delete(customerProjectProduct);
	}
	
}