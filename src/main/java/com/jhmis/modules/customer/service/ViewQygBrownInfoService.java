/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.ViewQygBrownInfo;
import com.jhmis.modules.customer.mapper.ViewQygBrownInfoMapper;

/**
 * hps视图工程版可验收列表Service
 * @author hdx
 * @version 2019-05-24
 */
@Service
@Transactional(readOnly = true)
public class ViewQygBrownInfoService extends CrudService<ViewQygBrownInfoMapper, ViewQygBrownInfo> {

	public ViewQygBrownInfo get(String id) {
		return super.get(id);
	}
	
	public List<ViewQygBrownInfo> findList(ViewQygBrownInfo viewQygBrownInfo) {
		return super.findList(viewQygBrownInfo);
	}
	
	public Page<ViewQygBrownInfo> findPage(Page<ViewQygBrownInfo> page, ViewQygBrownInfo viewQygBrownInfo) {
		return super.findPage(page, viewQygBrownInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ViewQygBrownInfo viewQygBrownInfo) {	
		super.save(viewQygBrownInfo);
	}
	@Transactional(readOnly = false)
	public void update(ViewQygBrownInfo viewQygBrownInfo) {	
		mapper.update(viewQygBrownInfo);
	}
	@Transactional(readOnly = false)
	public void insert(ViewQygBrownInfo viewQygBrownInfo) {	
		mapper.insert(viewQygBrownInfo);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(ViewQygBrownInfo viewQygBrownInfo) {
		super.delete(viewQygBrownInfo);
	}
	
}