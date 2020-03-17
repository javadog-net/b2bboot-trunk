/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.ViewQygBrownItem;
import com.jhmis.modules.customer.mapper.ViewQygBrownItemMapper;

/**
 * 工程版信息详情视图Service
 * @author hdx
 * @version 2019-05-29
 */
@Service
@Transactional(readOnly = true)
public class ViewQygBrownItemService extends CrudService<ViewQygBrownItemMapper, ViewQygBrownItem> {

	public ViewQygBrownItem get(String id) {
		return super.get(id);
	}

	@Transactional(readOnly = false)
	public List<ViewQygBrownItem> findList(ViewQygBrownItem viewQygBrownItem) {
		return super.findList(viewQygBrownItem);
	}
	
	public Page<ViewQygBrownItem> findPage(Page<ViewQygBrownItem> page, ViewQygBrownItem viewQygBrownItem) {
		return super.findPage(page, viewQygBrownItem);
	}
	
	@Transactional(readOnly = false)
	public void save(ViewQygBrownItem viewQygBrownItem) {
		mapper.insert(viewQygBrownItem);
	}
	
	@Transactional(readOnly = false)
	public void delete(ViewQygBrownItem viewQygBrownItem) {
		super.delete(viewQygBrownItem);
	}

	public List<ViewQygBrownItem> findByMine(ViewQygBrownItem viewQygBrownItem) {
		return mapper.findByMine(viewQygBrownItem);
	}

	@Transactional(readOnly = false)
	public void saveCopy(ViewQygBrownItem viewQygBrownItem) {
		mapper.insert(viewQygBrownItem);
	}
}