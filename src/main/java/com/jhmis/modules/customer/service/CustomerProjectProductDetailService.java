/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.CustomerProjectProductDetail;
import com.jhmis.modules.customer.mapper.CustomerProjectProductDetailMapper;

/**
 * 客单漏斗项目产品详情Service
 * @author hdx
 * @version 2019-04-16
 */
@Service
@Transactional(readOnly = true)
public class CustomerProjectProductDetailService extends CrudService<CustomerProjectProductDetailMapper, CustomerProjectProductDetail> {

	public CustomerProjectProductDetail get(String id) {
		return super.get(id);
	}
	
	public List<CustomerProjectProductDetail> findList(CustomerProjectProductDetail customerProjectProductDetail) {
		return super.findList(customerProjectProductDetail);
	}
	
	public Page<CustomerProjectProductDetail> findPage(Page<CustomerProjectProductDetail> page, CustomerProjectProductDetail customerProjectProductDetail) {
		return super.findPage(page, customerProjectProductDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerProjectProductDetail customerProjectProductDetail) {
		super.save(customerProjectProductDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerProjectProductDetail customerProjectProductDetail) {
		super.delete(customerProjectProductDetail);
	}
	
}