/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.AppIndustry;
import com.jhmis.modules.wechat.mapper.AppIndustryMapper;

/**
 * aService
 * @author abc
 * @version 2019-06-25
 */
@Service
@Transactional(readOnly = true)
public class AppIndustryService extends CrudService<AppIndustryMapper, AppIndustry> {

	@Autowired AppIndustryMapper appIndustryMapper;
	
	public AppIndustry get(String id) {
		return super.get(id);
	}
	
	public String findOnelevelidByIndustry(String industry) {
		return appIndustryMapper.findOnelevelidByIndustry(industry);
	}
	
	public List<AppIndustry> findList(AppIndustry appIndustry) {
		return super.findList(appIndustry);
	}
	
	public Page<AppIndustry> findPage(Page<AppIndustry> page, AppIndustry appIndustry) {
		return super.findPage(page, appIndustry);
	}
	
	@Transactional(readOnly = false)
	public void save(AppIndustry appIndustry) {
		super.save(appIndustry);
	}
	
	@Transactional(readOnly = false)
	public void delete(AppIndustry appIndustry) {
		super.delete(appIndustry);
	}
	
}