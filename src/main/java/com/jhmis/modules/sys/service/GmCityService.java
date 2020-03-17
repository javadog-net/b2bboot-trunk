/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.sys.entity.GmCity;
import com.jhmis.modules.sys.mapper.GmCityMapper;

/**
 * 工贸城市Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class GmCityService extends CrudService<GmCityMapper, GmCity> {
@Autowired
private GmCityMapper mapper;
	public GmCity get(String id) {
		return super.get(id);
	}
	
	public List<GmCity> findList(GmCity gmCity) {
		return super.findList(gmCity);
	}
	
	public Page<GmCity> findPage(Page<GmCity> page, GmCity gmCity) {
		return super.findPage(page, gmCity);
	}
	
	@Transactional(readOnly = false)
	public void save(GmCity gmCity) {
		super.save(gmCity);
	}
	
	@Transactional(readOnly = false)
	public void delete(GmCity gmCity) {
		super.delete(gmCity);
	}

	public GmCity findByCityId(String cityId) {
		// TODO Auto-generated method stub
		return mapper.findByCityId(cityId);
	}
	
}