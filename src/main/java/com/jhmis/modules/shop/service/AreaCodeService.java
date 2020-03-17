/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.AreaCode;
import com.jhmis.modules.shop.mapper.AreaCodeMapper;

/**
 * 省市区码表Service
 * @author hdx
 * @version 2019-03-28
 */
@Service
@Transactional(readOnly = true)
public class AreaCodeService extends CrudService<AreaCodeMapper, AreaCode> {

	public AreaCode get(String id) {
		return super.get(id);
	}
	
	public List<AreaCode> findList(AreaCode areaCode) {
		return super.findList(areaCode);
	}
	
	public Page<AreaCode> findPage(Page<AreaCode> page, AreaCode areaCode) {
		return super.findPage(page, areaCode);
	}
	
	@Transactional(readOnly = false)
	public void save(AreaCode areaCode) {
		super.save(areaCode);
	}
	
	@Transactional(readOnly = false)
	public void delete(AreaCode areaCode) {
		super.delete(areaCode);
	}
	
}