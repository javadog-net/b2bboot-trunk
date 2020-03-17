/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.Payment;
import com.jhmis.modules.shop.mapper.PaymentMapper;

/**
 * 支付方式Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class PaymentService extends CrudService<PaymentMapper, Payment> {

	public Payment get(String id) {
		return super.get(id);
	}
	
	public List<Payment> findList(Payment payment) {
		return super.findList(payment);
	}
	
	public Page<Payment> findPage(Page<Payment> page, Payment payment) {
		return super.findPage(page, payment);
	}
	
	@Transactional(readOnly = false)
	public void save(Payment payment) {
		super.save(payment);
	}
	
	@Transactional(readOnly = false)
	public void delete(Payment payment) {
		super.delete(payment);
	}
	
}