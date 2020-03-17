/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.IndustryProducts;
import com.jhmis.modules.customer.mapper.IndustryProductsMapper;

/**
 * 产业产品对照Service
 * @author mll
 * @version 2019-07-19
 */
@Service
@Transactional(readOnly = true)
public class IndustryProductsService extends CrudService<IndustryProductsMapper, IndustryProducts> {

	public IndustryProducts get(String id) {
		return super.get(id);
	}
	
	public List<IndustryProducts> findList(IndustryProducts industryProducts) {
		return super.findList(industryProducts);
	}
	
	public Page<IndustryProducts> findPage(Page<IndustryProducts> page, IndustryProducts industryProducts) {
		return super.findPage(page, industryProducts);
	}
	
	@Transactional(readOnly = false)
	public void save(IndustryProducts industryProducts) {
		super.save(industryProducts);
	}
	
	@Transactional(readOnly = false)
	public void delete(IndustryProducts industryProducts) {
		super.delete(industryProducts);
	}
	
}