/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.IndustryCode;
import com.jhmis.modules.customer.mapper.IndustryCodeMapper;

/**
 * 行业类别Service
 * @author hdx
 * @version 2019-04-27
 */
@Service
@Transactional(readOnly = true)
public class IndustryCodeService extends CrudService<IndustryCodeMapper, IndustryCode> {

	public IndustryCode get(String id) {
		return super.get(id);
	}
	
	public List<IndustryCode> findList(IndustryCode industryCode) {
		return super.findList(industryCode);
	}
	
	public Page<IndustryCode> findPage(Page<IndustryCode> page, IndustryCode industryCode) {
		return super.findPage(page, industryCode);
	}
	
	@Transactional(readOnly = false)
	public void save(IndustryCode industryCode) {
		super.save(industryCode);
	}
	
	@Transactional(readOnly = false)
	public void delete(IndustryCode industryCode) {
		super.delete(industryCode);
	}
	
}