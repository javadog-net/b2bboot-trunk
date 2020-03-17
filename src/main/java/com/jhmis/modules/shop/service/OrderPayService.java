/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jhmis.common.config.Global;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.OrderGoods;
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.mapper.OrderGoodsMapper;
import com.jhmis.modules.shop.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.OrderPay;
import com.jhmis.modules.shop.mapper.OrderPayMapper;

/**
 * 订单支付Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class OrderPayService extends CrudService<OrderPayMapper, OrderPay> {
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderGoodsMapper orderGoodsMapper;

	public OrderPay get(String id) {
		return super.get(id);
	}
	
	public List<OrderPay> findList(OrderPay orderPay) {
		return super.findList(orderPay);
	}
	
	public Page<OrderPay> findPage(Page<OrderPay> page, OrderPay orderPay,Orders orders) {
		dataRuleFilter(orderPay);
		orderPay.setPage(page);
		List<OrderPay> orderPayList = mapper.findOrdersPayList(orderPay);
		List<OrderPay> payResult = new ArrayList<>();
		for(OrderPay pay:orderPayList) {
			double totalAmount = 0;
			List<Orders> ordersList = pay.getOrdersList();
			for(Orders o :ordersList){
				totalAmount += o.getOrderAmount();
			}
			pay.setTotalAmount(totalAmount);
			payResult.add(pay);
		}
		page.setList(payResult);
		page.setCount(mapper.getOrderPayCount(orderPay));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(OrderPay orderPay) {
		super.save(orderPay);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderPay orderPay) {
		super.delete(orderPay);
	}
	@Transactional(readOnly = false)
	public void updatePayState(OrderPay orderPay, String inner_trade_no){
		mapper.update(orderPay);
		Orders order = new Orders();
		order.setPaySn(orderPay.getPaySn());
		List<Orders> ordersList = ordersMapper.findList(order);
		for(Orders orders:ordersList){
			if(Global.ORDER_STATE_NEW == orders.getOrderState()){
				orders.setOrderState(Global.ORDER_STATE_PAY_FINISHED);
				orders.setApiPayState(Global.KJT_API_PAY_STATE_SUCCESS);
				orders.setApiPayDate(new Date());
				orders.setTradeNo(inner_trade_no);
				ordersMapper.updateOrdersState(orders);
			}
		}
	}
}