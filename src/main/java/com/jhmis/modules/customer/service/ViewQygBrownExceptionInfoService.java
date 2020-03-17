/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.ViewQygBrownExceptionInfo;
import com.jhmis.modules.customer.mapper.ViewQygBrownExceptionInfoMapper;

/**
 * hps视图工程版可验收列表Service
 * @author hdx
 * @version 2019-05-24
 */
@Service
@Transactional(readOnly = true)
public class ViewQygBrownExceptionInfoService extends CrudService<ViewQygBrownExceptionInfoMapper, ViewQygBrownExceptionInfo> {

	public ViewQygBrownExceptionInfo get(String id) {
		return super.get(id);
	}
	
	public List<ViewQygBrownExceptionInfo> findList(ViewQygBrownExceptionInfo viewQygBrownExceptionInfo) {
		return super.findList(viewQygBrownExceptionInfo);
	}
	
	public Page<ViewQygBrownExceptionInfo> findPage(Page<ViewQygBrownExceptionInfo> page, ViewQygBrownExceptionInfo viewQygBrownExceptionInfo) {
		return super.findPage(page, viewQygBrownExceptionInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ViewQygBrownExceptionInfo viewQygBrownExceptionInfo) {	
		super.save(viewQygBrownExceptionInfo);
	}
	@Transactional(readOnly = false)
	public void update(ViewQygBrownExceptionInfo viewQygBrownExceptionInfo) {	
		mapper.update(viewQygBrownExceptionInfo);
	}
	@Transactional(readOnly = false)
	public void insert(ViewQygBrownExceptionInfo viewQygBrownExceptionInfo) {	
		mapper.insert(viewQygBrownExceptionInfo);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(ViewQygBrownExceptionInfo viewQygBrownExceptionInfo) {
		super.delete(viewQygBrownExceptionInfo);
	}
	
}