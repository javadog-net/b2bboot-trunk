/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.old.entity.OldShopMsgCustcodeOrder;
import com.jhmis.modules.old.mapper.OldShopMsgCustcodeOrderMapper;

/**
 * oldService
 * @author old
 * @version 2019-12-05
 */
@Service
@Transactional(readOnly = true)
public class OldShopMsgCustcodeOrderService extends CrudService<OldShopMsgCustcodeOrderMapper, OldShopMsgCustcodeOrder> {

	public OldShopMsgCustcodeOrder get(String id) {
		return super.get(id);
	}
	
	public List<OldShopMsgCustcodeOrder> findList(OldShopMsgCustcodeOrder oldShopMsgCustcodeOrder) {
		return super.findList(oldShopMsgCustcodeOrder);
	}
	
	public Page<OldShopMsgCustcodeOrder> findPage(Page<OldShopMsgCustcodeOrder> page, OldShopMsgCustcodeOrder oldShopMsgCustcodeOrder) {
		return super.findPage(page, oldShopMsgCustcodeOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(OldShopMsgCustcodeOrder oldShopMsgCustcodeOrder) {
		super.save(oldShopMsgCustcodeOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(OldShopMsgCustcodeOrder oldShopMsgCustcodeOrder) {
		super.delete(oldShopMsgCustcodeOrder);
	}
	
}