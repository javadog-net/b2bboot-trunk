/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.TOrderInfo;
import com.jhmis.modules.customer.mapper.TOrderInfoMapper;

/**
 * 巨商会订单信息推送表Service
 * @author hdx
 * @version 2020-01-20
 */
@Service
@Transactional(readOnly = true)
public class TOrderInfoService extends CrudService<TOrderInfoMapper, TOrderInfo> {

	public TOrderInfo get(String id) {
		return super.get(id);
	}
	
	public List<TOrderInfo> findList(TOrderInfo tOrderInfo) {
		return super.findList(tOrderInfo);
	}

	public List<TOrderInfo> findTaskList(TOrderInfo tOrderInfo) {
		return mapper.findTaskList(tOrderInfo);
	}
	
	public Page<TOrderInfo> findPage(Page<TOrderInfo> page, TOrderInfo tOrderInfo) {
		return super.findPage(page, tOrderInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(TOrderInfo tOrderInfo) {
		super.save(tOrderInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(TOrderInfo tOrderInfo) {
		super.delete(tOrderInfo);
	}
	
}