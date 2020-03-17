/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.TOrderDetail;
import com.jhmis.modules.customer.mapper.TOrderDetailMapper;

/**
 * 巨商会订单信息详情推送表Service
 * @author hdx
 * @version 2020-01-20
 */
@Service
@Transactional(readOnly = true)
public class TOrderDetailService extends CrudService<TOrderDetailMapper, TOrderDetail> {

	public TOrderDetail get(String id) {
		return super.get(id);
	}
	
	public List<TOrderDetail> findList(TOrderDetail tOrderDetail) {
		return super.findList(tOrderDetail);
	}

	public List<TOrderDetail> findTaskList(TOrderDetail tOrderDetail) {
		return mapper.findTaskList(tOrderDetail);
	}

	
	public Page<TOrderDetail> findPage(Page<TOrderDetail> page, TOrderDetail tOrderDetail) {
		return super.findPage(page, tOrderDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(TOrderDetail tOrderDetail) {
		super.save(tOrderDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(TOrderDetail tOrderDetail) {
		super.delete(tOrderDetail);
	}
	
}