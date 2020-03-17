/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.service.shopproject;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.process.entity.shopproject.ShopProject;
import com.jhmis.modules.process.mapper.shopproject.ShopProjectMapper;

/**
 * hps需求汇总Service
 * @author hdx
 * @version 2019-11-04
 */
@Service
@Transactional(readOnly = true)
public class ShopProjectService extends CrudService<ShopProjectMapper, ShopProject> {

	public ShopProject get(String id) {
		return super.get(id);
	}
	
	public List<ShopProject> findList(ShopProject shopProject) {
		return super.findList(shopProject);
	}
	
	public Page<ShopProject> findPage(Page<ShopProject> page, ShopProject shopProject) {
		return super.findPage(page, shopProject);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopProject shopProject) {
		super.save(shopProject);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopProject shopProject) {
		super.delete(shopProject);
	}
	
}