/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.OpportunityPointSk;
import com.jhmis.modules.customer.mapper.OpportunityPointSkMapper;

/**
 * 商空机会点Service
 * @author mll
 * @version 2019-08-14
 */
@Service
@Transactional(readOnly = true)
public class OpportunityPointSkService extends CrudService<OpportunityPointSkMapper, OpportunityPointSk> {

	public OpportunityPointSk get(String id) {
		return super.get(id);
	}
	
	public List<OpportunityPointSk> findList(OpportunityPointSk opportunityPointSk) {
		return super.findList(opportunityPointSk);
	}
	
	public Page<OpportunityPointSk> findPage(Page<OpportunityPointSk> page, OpportunityPointSk opportunityPointSk) {
		return super.findPage(page, opportunityPointSk);
	}
	
	@Transactional(readOnly = false)
	public void save(OpportunityPointSk opportunityPointSk) {
		super.save(opportunityPointSk);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpportunityPointSk opportunityPointSk) {
		super.delete(opportunityPointSk);
	}
	
}