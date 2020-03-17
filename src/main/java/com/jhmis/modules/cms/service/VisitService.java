/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.Visit;
import com.jhmis.modules.cms.mapper.VisitMapper;

/**
 * 访问记录Service
 * @author lydia
 * @version 2019-10-14
 */
@Service
@Transactional(readOnly = true)
public class VisitService extends CrudService<VisitMapper, Visit> {

	public Visit get(String id) {
		return super.get(id);
	}
	
	public List<Visit> findList(Visit visit) {
		return super.findList(visit);
	}
	
	public Page<Visit> findPage(Page<Visit> page, Visit visit) {
		return super.findPage(page, visit);
	}
	
	@Transactional(readOnly = false)
	public void save(Visit visit) {
		super.save(visit);
	}
	
	@Transactional(readOnly = false)
	public void delete(Visit visit) {
		super.delete(visit);
	}
	
}