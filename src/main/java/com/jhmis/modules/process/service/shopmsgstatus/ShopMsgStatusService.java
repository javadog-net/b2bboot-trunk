/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.service.shopmsgstatus;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.process.entity.shopmsgstatus.ShopMsgStatus;
import com.jhmis.modules.process.mapper.shopmsgstatus.ShopMsgStatusMapper;

/**
 * 需求状态履历表Service
 * @author hdx
 * @version 2019-09-23
 */
@Service
@Transactional(readOnly = true)
public class ShopMsgStatusService extends CrudService<ShopMsgStatusMapper, ShopMsgStatus> {

	public ShopMsgStatus get(String id) {
		return super.get(id);
	}
	
	public List<ShopMsgStatus> findList(ShopMsgStatus shopMsgStatus) {
		return super.findList(shopMsgStatus);
	}
	
	public Page<ShopMsgStatus> findPage(Page<ShopMsgStatus> page, ShopMsgStatus shopMsgStatus) {
		return super.findPage(page, shopMsgStatus);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopMsgStatus shopMsgStatus) {
		super.save(shopMsgStatus);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopMsgStatus shopMsgStatus) {
		super.delete(shopMsgStatus);
	}
	
}