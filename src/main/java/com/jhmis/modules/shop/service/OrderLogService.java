/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.OrderLog;
import com.jhmis.modules.shop.mapper.OrderLogMapper;

/**
 * 订单日志Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class OrderLogService extends CrudService<OrderLogMapper, OrderLog> {

	public OrderLog get(String id) {
		return super.get(id);
	}
	
	public List<OrderLog> findList(OrderLog orderLog) {
		return super.findList(orderLog);
	}
	
	public Page<OrderLog> findPage(Page<OrderLog> page, OrderLog orderLog) {
		return super.findPage(page, orderLog);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderLog orderLog) {
		super.save(orderLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderLog orderLog) {
		super.delete(orderLog);
	}
	
}