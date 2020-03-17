/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.Mdm88;
import com.jhmis.modules.shop.mapper.Mdm88Mapper;

/**
 * 88码处理Service
 * @author mll
 * @version 2019-09-16
 */
@Service
@Transactional(readOnly = true)
public class Mdm88Service extends CrudService<Mdm88Mapper, Mdm88> {

	public Mdm88 get(String id) {
		return super.get(id);
	}
	
	public List<Mdm88> findList(Mdm88 mdm88) {
		return super.findList(mdm88);
	}
	
	public Page<Mdm88> findPage(Page<Mdm88> page, Mdm88 mdm88) {
		return super.findPage(page, mdm88);
	}
	
	@Transactional(readOnly = false)
	public void save(Mdm88 mdm88) {
		super.save(mdm88);
	}
	
	@Transactional(readOnly = false)
	public void delete(Mdm88 mdm88) {
		super.delete(mdm88);
	}
	
}