/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.ViewQygBrown;
import com.jhmis.modules.customer.mapper.ViewQygBrownMapper;

/**
 * 工程版信息视图Service
 * @author hdx
 * @version 2019-05-29
 */
@Service
@Transactional(readOnly = true)
public class ViewQygBrownService extends CrudService<ViewQygBrownMapper, ViewQygBrown> {

	public ViewQygBrown get(String id) {
		return super.get(id);
	}
	
	public List<ViewQygBrown> findList(ViewQygBrown viewQygBrown) {
		return super.findList(viewQygBrown);
	}
	
	public Page<ViewQygBrown> findPage(Page<ViewQygBrown> page, ViewQygBrown viewQygBrown) {
		return super.findPage(page, viewQygBrown);
	}
	@Transactional(readOnly = false)
	public List<ViewQygBrown> findByMine(ViewQygBrown viewQygBrown) {
		return mapper.findByMine(viewQygBrown);
	}
	
	@Transactional(readOnly = false)
	public void save(ViewQygBrown viewQygBrown) {
		super.save(viewQygBrown);
	}
	
	@Transactional(readOnly = false)
	public void delete(ViewQygBrown viewQygBrown) {
		super.delete(viewQygBrown);
	}
	
}