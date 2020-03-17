/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.Date;
import java.util.List;

import com.jhmis.common.utils.IdGen;
import com.jhmis.modules.shop.entity.*;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;

import javax.annotation.Resource;

/**
 * 店铺评分Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class StoreEvaluateService extends CrudService<StoreEvaluateMapper, StoreEvaluate> {

	@Resource
	private StoreEvaluateMapper storeEvaluateMapper;
	@Resource
	private GoodsEvaluateMapper goodsEvaluateMapper;
	@Resource
	private OrdersMapper ordersMapper;
	@Resource
	private OrderGoodsMapper orderGoodsMapper;
	@Resource
	private GoodsMapper goodsMapper;




	public StoreEvaluate get(String id) {
		return super.get(id);
	}
	
	public List<StoreEvaluate> findList(StoreEvaluate storeEvaluate) {
		return super.findList(storeEvaluate);
	}
	
	public Page<StoreEvaluate> findPage(Page<StoreEvaluate> page, StoreEvaluate storeEvaluate) {
		return super.findPage(page, storeEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void save(StoreEvaluate storeEvaluate) {
		super.save(storeEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void delete(StoreEvaluate storeEvaluate) {
		super.delete(storeEvaluate);
		storeEvaluateMapper.delete(storeEvaluate);
	}

	@Transactional(readOnly = false)
	public boolean saveStoreEvaluate(OrderEvaluate orderEvaluate, Orders orders,PurchaserAccount currentAccount) {
		if(orderEvaluate.getGoodsEvaluateList()!=null && orderEvaluate.getGoodsEvaluateList().size()>0){
			for(GoodsEvaluate goodsEvaluate:orderEvaluate.getGoodsEvaluateList()){
				//计入订单编号
				goodsEvaluate.setOrderSn(orders.getOrderSn());
				OrderGoods orderGoods = new OrderGoods();
				orderGoods.setOrderId(orders.getId());
				orderGoods.setGoodsCode(goodsEvaluate.getGoodsCode());
				List<OrderGoods> orderGoodsList = orderGoodsMapper.findList(orderGoods);
				if(orderGoodsList!=null && orderGoodsList.size()>0){
					orderGoods = orderGoodsList.get(0);
				}
				//评价相关信息
				goodsEvaluate.setGoodsCode(orderGoods.getGoodsCode());
				goodsEvaluate.setStoreName(orderGoods.getStoreName());
				goodsEvaluate.setStoreId(orderGoods.getStoreId());
				goodsEvaluate.setMainPicUrl(orderGoods.getMainPicUrl());
				goodsEvaluate.setPurchaserAccountId(currentAccount.getId());
				goodsEvaluate.setAddtime(new Date());
				Goods goods = goodsMapper.findUniqueByProperty("code",goodsEvaluate.getGoodsCode());
				goodsEvaluate.setGoodsPrice(orderGoods.getPayPrice().toString());
				goodsEvaluate.setGoodsName(goods.getName());
				goodsEvaluate.setId(IdGen.uuid());
				goodsEvaluate.setIsanonymous(orderEvaluate.getIsanonymous());
				goodsEvaluate.setOrderId(orders.getId());
				//保存评价
				goodsEvaluateMapper.insert(goodsEvaluate);
				//更新订单
				orderGoods.setEvaluateStatus("1");
				orderGoodsMapper.update(orderGoods);
			}
		}
		//更新店铺评价表
		StoreEvaluate storeEvaluate = new StoreEvaluate();
		storeEvaluate.setOrderId(orders.getId());
		storeEvaluate.setOrderSn(orders.getOrderSn());
		storeEvaluate.setStoreId(orders.getStoreId());
		storeEvaluate.setStoreName(orders.getStoreName());
		storeEvaluate.setPurchaserId(currentAccount.getPurchaserId());
		storeEvaluate.setPurchaserAccountId(currentAccount.getId());
		storeEvaluate.setScoreProductQuality(orderEvaluate.getScoreProductQuality());
		storeEvaluate.setScoreDemandResponse(orderEvaluate.getScoreDemandResponse());
		storeEvaluate.setScoreDeliveryCredit(orderEvaluate.getScoreDeliveryCredit());
		storeEvaluate.setScoreSupplySpeed(orderEvaluate.getScoreSupplySpeed());
		storeEvaluate.setScoreCustomerService(orderEvaluate.getScoreCustomerService());
		storeEvaluate.setIsanonymous(orderEvaluate.getIsanonymous());
		storeEvaluate.setAddtime(new Date());
		storeEvaluate.setId((IdGen.uuid()));
		storeEvaluateMapper.insert(storeEvaluate);
		//更新订单评价状态
		orders.setEvaluationState("1");
		ordersMapper.update(orders);
		return true;
	}


	
}