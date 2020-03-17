/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.service.dispatcher;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.mapper.dispatcher.ShopMsgDispatcherMapper;

/**
 * 派单相关Service
 * @author hdx
 * @version 2019-09-06
 */
@Service
@Transactional(readOnly = true)
public class ShopMsgDispatcherService extends CrudService<ShopMsgDispatcherMapper, ShopMsgDispatcher> {

	public ShopMsgDispatcher get(String id) {
		return super.get(id);
	}
	
	public List<ShopMsgDispatcher> findList(ShopMsgDispatcher shopMsgDispatcher) {
		return super.findList(shopMsgDispatcher);
	}
	
	public Page<ShopMsgDispatcher> findPage(Page<ShopMsgDispatcher> page, ShopMsgDispatcher shopMsgDispatcher) {
		return super.findPage(page, shopMsgDispatcher);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopMsgDispatcher shopMsgDispatcher) {
		super.save(shopMsgDispatcher);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopMsgDispatcher shopMsgDispatcher) {
		super.delete(shopMsgDispatcher);
	}
	
}