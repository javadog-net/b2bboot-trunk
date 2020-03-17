/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.old.entity.OldShopMsg;
import com.jhmis.modules.old.mapper.OldShopMsgMapper;

/**
 * oldService
 * @author old
 * @version 2019-12-07
 */
@Service
@Transactional(readOnly = true)
public class OldShopMsgService extends CrudService<OldShopMsgMapper, OldShopMsg> {

	public OldShopMsg get(String id) {
		return super.get(id);
	}
	
	public List<OldShopMsg> findList(OldShopMsg oldShopMsg) {
		return super.findList(oldShopMsg);
	}
	
	public Page<OldShopMsg> findPage(Page<OldShopMsg> page, OldShopMsg oldShopMsg) {
		return super.findPage(page, oldShopMsg);
	}
	
	@Transactional(readOnly = false)
	public void save(OldShopMsg oldShopMsg) {
		super.save(oldShopMsg);
	}
	
	@Transactional(readOnly = false)
	public void delete(OldShopMsg oldShopMsg) {
		super.delete(oldShopMsg);
	}
	
}