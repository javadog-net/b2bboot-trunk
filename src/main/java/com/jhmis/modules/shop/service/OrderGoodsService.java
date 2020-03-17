/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.OrderGoods;
import com.jhmis.modules.shop.mapper.OrderGoodsMapper;

/**
 * 订单商品Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class OrderGoodsService extends CrudService<OrderGoodsMapper, OrderGoods> {

	public OrderGoods get(String id) {
		return super.get(id);
	}
	
	public List<OrderGoods> findList(OrderGoods orderGoods) {
		return super.findList(orderGoods);
	}
	
	public Page<OrderGoods> findPage(Page<OrderGoods> page, OrderGoods orderGoods) {
		return super.findPage(page, orderGoods);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderGoods orderGoods) {
		super.save(orderGoods);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderGoods orderGoods) {
		super.delete(orderGoods);
	}
	
}