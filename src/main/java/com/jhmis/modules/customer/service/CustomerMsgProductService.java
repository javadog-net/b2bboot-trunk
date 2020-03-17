/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.CustomerMsgProduct;
import com.jhmis.modules.customer.mapper.CustomerMsgProductMapper;

/**
 * 客单产品关联表Service
 * @author hdx
 * @version 2019-04-16
 */
@Service
@Transactional(readOnly = true)
public class CustomerMsgProductService extends CrudService<CustomerMsgProductMapper, CustomerMsgProduct> {

	@Autowired
	private CustomerMsgProductMapper customerMsgProductMapper;
	
	public CustomerMsgProduct get(String id) {
		return super.get(id);
	}
	
	public List<CustomerMsgProduct> findList(CustomerMsgProduct customerMsgProduct) {
		return super.findList(customerMsgProduct);
	}
	
	public Page<CustomerMsgProduct> findPage(Page<CustomerMsgProduct> page, CustomerMsgProduct customerMsgProduct) {
		return super.findPage(page, customerMsgProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerMsgProduct customerMsgProduct) {
		super.save(customerMsgProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerMsgProduct customerMsgProduct) {
		super.delete(customerMsgProduct);
	}
	
	@Transactional(readOnly = false)
	public void deleteByMsgid(String customerMsgId){
		customerMsgProductMapper.deleteByMsgid(customerMsgId);
	}
	
}