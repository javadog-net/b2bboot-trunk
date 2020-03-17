/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.RefundReturn;
import com.jhmis.modules.shop.mapper.RefundReturnMapper;

/**
 * 退款管理Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class RefundReturnService extends CrudService<RefundReturnMapper, RefundReturn> {

	public RefundReturn get(String id) {
		return super.get(id);
	}
	
	public List<RefundReturn> findList(RefundReturn refundReturn) {
		return super.findList(refundReturn);
	}
	
	public Page<RefundReturn> findPage(Page<RefundReturn> page, RefundReturn refundReturn) {
		return super.findPage(page, refundReturn);
	}
	
	@Transactional(readOnly = false)
	public void save(RefundReturn refundReturn) {
		super.save(refundReturn);
	}
	
	@Transactional(readOnly = false)
	public void delete(RefundReturn refundReturn) {
		super.delete(refundReturn);
	}
	
}