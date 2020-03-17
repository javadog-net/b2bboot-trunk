/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.CustomerProjectInfo;
import com.jhmis.modules.customer.mapper.CustomerProjectInfoMapper;

/**
 * 客单漏斗项目Service
 * @author hdx
 * @version 2019-04-16
 */
@Service
@Transactional(readOnly = true)
public class CustomerProjectInfoService extends CrudService<CustomerProjectInfoMapper, CustomerProjectInfo> {

	public CustomerProjectInfo get(String id) {
		return super.get(id);
	}

	public List<CustomerProjectInfo> findList(CustomerProjectInfo customerProjectInfo) {
		return super.findList(customerProjectInfo);
	}

	public List<CustomerProjectInfo> findListTask(CustomerProjectInfo customerProjectInfo) {
		return mapper.findListTask(customerProjectInfo);
	}
	
	public Page<CustomerProjectInfo> findPage(Page<CustomerProjectInfo> page, CustomerProjectInfo customerProjectInfo) {
		return super.findPage(page, customerProjectInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerProjectInfo customerProjectInfo) {
		super.save(customerProjectInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerProjectInfo customerProjectInfo) {
		super.delete(customerProjectInfo);
	}
	
}