/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.Industry;
import com.jhmis.modules.shop.mapper.IndustryMapper;

/**
 * 行业信息管理Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class IndustryService extends CrudService<IndustryMapper, Industry> {

	public Industry get(String id) {
		return super.get(id);
	}
	
	public List<Industry> findList(Industry industry) {
		return super.findList(industry);
	}
	
	public Page<Industry> findPage(Page<Industry> page, Industry industry) {
		return super.findPage(page, industry);
	}
	
	@Transactional(readOnly = false)
	public void save(Industry industry) {
		super.save(industry);
	}
	
	@Transactional(readOnly = false)
	public void delete(Industry industry) {
		super.delete(industry);
	}
	
}