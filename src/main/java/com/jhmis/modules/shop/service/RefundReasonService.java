/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.RefundReason;
import com.jhmis.modules.shop.mapper.RefundReasonMapper;

/**
 * 退货原因管理Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class RefundReasonService extends CrudService<RefundReasonMapper, RefundReason> {

	public RefundReason get(String id) {
		return super.get(id);
	}
	
	public List<RefundReason> findList(RefundReason refundReason) {
		return super.findList(refundReason);
	}
	
	public Page<RefundReason> findPage(Page<RefundReason> page, RefundReason refundReason) {
		return super.findPage(page, refundReason);
	}
	
	@Transactional(readOnly = false)
	public void save(RefundReason refundReason) {
		super.save(refundReason);
	}
	
	@Transactional(readOnly = false)
	public void delete(RefundReason refundReason) {
		super.delete(refundReason);
	}
	
}