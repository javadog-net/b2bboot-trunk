/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.old.entity.OldShopMsgStatus;
import com.jhmis.modules.old.mapper.OldShopMsgStatusMapper;

/**
 * oldService
 * @author old
 * @version 2019-11-29
 */
@Service
@Transactional(readOnly = true)
public class OldShopMsgStatusService extends CrudService<OldShopMsgStatusMapper, OldShopMsgStatus> {

	public OldShopMsgStatus get(String id) {
		return super.get(id);
	}
	
	public List<OldShopMsgStatus> findList(OldShopMsgStatus oldShopMsgStatus) {
		return super.findList(oldShopMsgStatus);
	}
	
	public Page<OldShopMsgStatus> findPage(Page<OldShopMsgStatus> page, OldShopMsgStatus oldShopMsgStatus) {
		return super.findPage(page, oldShopMsgStatus);
	}
	
	@Transactional(readOnly = false)
	public void save(OldShopMsgStatus oldShopMsgStatus) {
		super.save(oldShopMsgStatus);
	}
	
	@Transactional(readOnly = false)
	public void delete(OldShopMsgStatus oldShopMsgStatus) {
		super.delete(oldShopMsgStatus);
	}
	
}