/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import com.jhmis.common.config.Global;
import com.jhmis.modules.shop.entity.OrderGoods;
import com.jhmis.modules.shop.mapper.OrderGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.OrderGoodsExchange;
import com.jhmis.modules.shop.mapper.OrderGoodsExchangeMapper;

/**
 * Service
 * @author cuihj
 * @version 2018-08-20
 */
@Service
@Transactional(readOnly = true)
public class OrderGoodsExchangeService extends CrudService<OrderGoodsExchangeMapper, OrderGoodsExchange> {
	@Autowired
	private OrderGoodsMapper  orderGoodsMapper;

	public OrderGoodsExchange get(String id) {
		return super.get(id);
	}
	
	public List<OrderGoodsExchange> findList(OrderGoodsExchange orderGoodsExchange) {
		return super.findList(orderGoodsExchange);
	}
	
	public Page<OrderGoodsExchange> findPage(Page<OrderGoodsExchange> page, OrderGoodsExchange orderGoodsExchange) {
		return super.findPage(page, orderGoodsExchange);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderGoodsExchange orderGoodsExchange) {
		super.save(orderGoodsExchange);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderGoodsExchange orderGoodsExchange) {
		super.delete(orderGoodsExchange);
	}

	@Transactional(readOnly = false)
	public void exchangeApply(OrderGoodsExchange orderGoodsExchange){
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setId(orderGoodsExchange.getOrderGoodsId());
        orderGoods = orderGoodsMapper.get(orderGoods);
		orderGoods.setIsExchange(Global.GOODS_EXCHANGE_APPLY);
		orderGoodsMapper.update(orderGoods);
		super.save(orderGoodsExchange);
	}
	@Transactional(readOnly = false)
	public void auditExchangeApply(OrderGoodsExchange orderGoodsExchange){
		OrderGoods orderGoods = new OrderGoods();
		orderGoods.setId(orderGoodsExchange.getOrderGoodsId());
		orderGoods = orderGoodsMapper.get(orderGoods);
		if(Global.AUDIT_STATE_OK.equals(orderGoodsExchange.getAuditDesc())){
			orderGoods.setIsExchange(Global.GOODS_EXCHANGE_APPLY_PASS);
		}
		if(Global.AUDIT_STATE_NO.equals(orderGoodsExchange.getAuditDesc())){
			orderGoods.setIsExchange(Global.GOODS_EXCHANGE_APPLY_UNPASS);
		}
		orderGoodsMapper.update(orderGoods);
		super.save(orderGoodsExchange);
	}
}