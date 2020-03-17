/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.old.entity.OldShopMsgDispatcher;
import com.jhmis.modules.old.mapper.OldShopMsgDispatcherMapper;

/**
 * oldService
 * @author old
 * @version 2019-11-29
 */
@Service
@Transactional(readOnly = true)
public class OldShopMsgDispatcherService extends CrudService<OldShopMsgDispatcherMapper, OldShopMsgDispatcher> {

	public OldShopMsgDispatcher get(String id) {
		return super.get(id);
	}
	
	public List<OldShopMsgDispatcher> findList(OldShopMsgDispatcher oldShopMsgDispatcher) {
		return super.findList(oldShopMsgDispatcher);
	}
	
	public Page<OldShopMsgDispatcher> findPage(Page<OldShopMsgDispatcher> page, OldShopMsgDispatcher oldShopMsgDispatcher) {
		return super.findPage(page, oldShopMsgDispatcher);
	}
	
	@Transactional(readOnly = false)
	public void save(OldShopMsgDispatcher oldShopMsgDispatcher) {
		super.save(oldShopMsgDispatcher);
	}
	
	@Transactional(readOnly = false)
	public void delete(OldShopMsgDispatcher oldShopMsgDispatcher) {
		super.delete(oldShopMsgDispatcher);
	}
	
}