/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.old.entity.OldShopMsgZykc;
import com.jhmis.modules.old.mapper.OldShopMsgZykcMapper;

/**
 * oldService
 * @author old
 * @version 2019-11-29
 */
@Service
@Transactional(readOnly = true)
public class OldShopMsgZykcService extends CrudService<OldShopMsgZykcMapper, OldShopMsgZykc> {

	public OldShopMsgZykc get(String id) {
		return super.get(id);
	}
	
	public List<OldShopMsgZykc> findList(OldShopMsgZykc oldShopMsgZykc) {
		return super.findList(oldShopMsgZykc);
	}
	
	public Page<OldShopMsgZykc> findPage(Page<OldShopMsgZykc> page, OldShopMsgZykc oldShopMsgZykc) {
		return super.findPage(page, oldShopMsgZykc);
	}
	
	@Transactional(readOnly = false)
	public void save(OldShopMsgZykc oldShopMsgZykc) {
		super.save(oldShopMsgZykc);
	}
	
	@Transactional(readOnly = false)
	public void delete(OldShopMsgZykc oldShopMsgZykc) {
		super.delete(oldShopMsgZykc);
	}
	
}