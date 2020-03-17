/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.CustomerComment;
import com.jhmis.modules.customer.mapper.CustomerCommentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 客单评论Service
 * @author tc
 * @version 2020-01-21
 */
@Service
@Transactional(readOnly = true)
public class CustomerCommentService extends CrudService<CustomerCommentMapper, CustomerComment> {

	public CustomerComment get(String id) {
		return super.get(id);
	}
	
	public List<CustomerComment> findList(CustomerComment customerComment) {
		return super.findList(customerComment);
	}
	
	public Page<CustomerComment> findPage(Page<CustomerComment> page, CustomerComment customerComment) {
		return super.findPage(page, customerComment);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerComment customerComment) {
		super.save(customerComment);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerComment customerComment) {
		super.delete(customerComment);
	}
	
}