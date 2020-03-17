/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import com.jhmis.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.ViewBusinessOpportunity;
import com.jhmis.modules.customer.mapper.ViewBusinessOpportunityMapper;

/**
 * 项目履约视图Service
 * @author h'd'x
 * @version 2020-02-13
 */
@Service
@Transactional(readOnly = true)
public class ViewBusinessOpportunityService extends CrudService<ViewBusinessOpportunityMapper, ViewBusinessOpportunity> {

	public ViewBusinessOpportunity get(String id) {
		return super.get(id);
	}
	
	public List<ViewBusinessOpportunity> findList(ViewBusinessOpportunity viewBusinessOpportunity) {
		return super.findList(viewBusinessOpportunity);
	}

	public List<ViewBusinessOpportunity> findTaskList(ViewBusinessOpportunity viewBusinessOpportunity) {
		return mapper.findTaskList(viewBusinessOpportunity);
	}

	public Page<ViewBusinessOpportunity> findPage(Page<ViewBusinessOpportunity> page, ViewBusinessOpportunity viewBusinessOpportunity) {
		return super.findPage(page, viewBusinessOpportunity);
	}
	
	@Transactional(readOnly = false)
	public void save(ViewBusinessOpportunity viewBusinessOpportunity) {
		ViewBusinessOpportunity vbo = mapper.get(viewBusinessOpportunity.getBrownCode());
		if(vbo==null){
			mapper.insert(viewBusinessOpportunity);
		}else{
			mapper.update(viewBusinessOpportunity);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ViewBusinessOpportunity viewBusinessOpportunity) {
		super.delete(viewBusinessOpportunity);
	}
	
}