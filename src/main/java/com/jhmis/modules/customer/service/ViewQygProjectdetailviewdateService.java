/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.ViewQygBrownExceptionInfo;
import com.jhmis.modules.customer.entity.ViewQygProjectdetailviewdate;
import com.jhmis.modules.customer.mapper.ViewQygProjectdetailviewdateMapper;

/**
 * aService
 * @author a
 * @version 2019-10-29
 */
@Service
@Transactional(readOnly = true)
public class ViewQygProjectdetailviewdateService extends CrudService<ViewQygProjectdetailviewdateMapper, ViewQygProjectdetailviewdate> {

	@Autowired
	ViewQygProjectdetailviewdateMapper viewQygProjectdetailviewdateMapper;
	
	public ViewQygProjectdetailviewdate get(String id) {
		return super.get(id);
	}
		
	public List<ViewQygProjectdetailviewdate> findList(ViewQygProjectdetailviewdate viewQygProjectdetailviewdate) {
		return super.findList(viewQygProjectdetailviewdate);
	}
	
	public Page<ViewQygProjectdetailviewdate> findPage(Page<ViewQygProjectdetailviewdate> page, ViewQygProjectdetailviewdate viewQygProjectdetailviewdate) {
		return super.findPage(page, viewQygProjectdetailviewdate);
	}
	
	@Transactional(readOnly = false)
	public void update(ViewQygProjectdetailviewdate viewQygProjectdetailviewdate) {	
		mapper.update(viewQygProjectdetailviewdate);
	}
	@Transactional(readOnly = false)
	public void insert(ViewQygProjectdetailviewdate viewQygProjectdetailviewdate) {	
		mapper.insert(viewQygProjectdetailviewdate);
	}
	
	@Transactional(readOnly = false)
	public void save(ViewQygProjectdetailviewdate viewQygProjectdetailviewdate) {
		super.save(viewQygProjectdetailviewdate);
	}
	
	@Transactional(readOnly = false)
	public void delete(ViewQygProjectdetailviewdate viewQygProjectdetailviewdate) {
		super.delete(viewQygProjectdetailviewdate);
	}
	
	public ViewQygProjectdetailviewdate getByProjectcode(String projectCode) {
		return viewQygProjectdetailviewdateMapper.getByProjectcode(projectCode);
	}
	
}