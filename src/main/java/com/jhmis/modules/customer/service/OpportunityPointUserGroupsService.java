/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.OpportunityPointUserGroups;
import com.jhmis.modules.customer.mapper.OpportunityPointUserGroupsMapper;

/**
 * 机会点，用户群Service
 * @author mll
 * @version 2019-07-19
 */
@Service
@Transactional(readOnly = true)
public class OpportunityPointUserGroupsService extends CrudService<OpportunityPointUserGroupsMapper, OpportunityPointUserGroups> {

	public OpportunityPointUserGroups get(String id) {
		return super.get(id);
	}
	
	public List<OpportunityPointUserGroups> findList(OpportunityPointUserGroups opportunityPointUserGroups) {
		return super.findList(opportunityPointUserGroups);
	}
	
	public Page<OpportunityPointUserGroups> findPage(Page<OpportunityPointUserGroups> page, OpportunityPointUserGroups opportunityPointUserGroups) {
		return super.findPage(page, opportunityPointUserGroups);
	}
	
	@Transactional(readOnly = false)
	public void save(OpportunityPointUserGroups opportunityPointUserGroups) {
		super.save(opportunityPointUserGroups);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpportunityPointUserGroups opportunityPointUserGroups) {
		super.delete(opportunityPointUserGroups);
	}
	
}