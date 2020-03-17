/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.Goods;
import com.jhmis.modules.shop.entity.GoodsEvaluate;
import com.jhmis.modules.shop.entity.OrderGoods;
import com.jhmis.modules.shop.entity.Orders;
import com.jhmis.modules.shop.mapper.GoodsEvaluateMapper;
import com.jhmis.modules.shop.mapper.GoodsMapper;
import com.jhmis.modules.shop.mapper.OrderGoodsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 商品评价Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class GoodsEvaluateService extends CrudService<GoodsEvaluateMapper, GoodsEvaluate> {

	@Resource
	OrderGoodsMapper orderGoodsMapper;

	@Resource
	GoodsEvaluateMapper goodsEvaluateMapper;

	@Resource
	GoodsMapper goodsMapper;

	public GoodsEvaluate get(String id) {
		return super.get(id);
	}
	
	public List<GoodsEvaluate> findList(GoodsEvaluate goodsEvaluate) {
		return super.findList(goodsEvaluate);
	}
	
	public Page<GoodsEvaluate> findPage(Page<GoodsEvaluate> page, GoodsEvaluate goodsEvaluate) {
		return super.findPage(page, goodsEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsEvaluate goodsEvaluate) {
		super.save(goodsEvaluate);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsEvaluate goodsEvaluate) {
		super.delete(goodsEvaluate);
	}

	@Transactional(readOnly = false)
	public boolean saveGoodsEvaluate(GoodsEvaluate goodsEvaluate,Orders orders,OrderGoods orderGoods){
		//计入订单编号
		goodsEvaluate.setOrderSn(orders.getOrderSn());
		//评价相关信息
		goodsEvaluate.setGoodsCode(orderGoods.getGoodsCode());
		goodsEvaluate.setStoreName(orderGoods.getStoreName());
		goodsEvaluate.setStoreId(orderGoods.getStoreId());
		goodsEvaluate.setMainPicUrl(orderGoods.getMainPicUrl());
		goodsEvaluate.setAddtime(new Date());
		Goods goods = goodsMapper.findUniqueByProperty("code",goodsEvaluate.getGoodsCode());
		goodsEvaluate.setGoodsPrice(orderGoods.getPayPrice().toString());
		goodsEvaluate.setGoodsName(goods.getName());
		goodsEvaluate.setOrderId(orderGoods.getOrderId());
		//保存评价
		super.save(goodsEvaluate);
		//更新订单
		orderGoods.setEvaluateStatus("1");
		orderGoodsMapper.update(orderGoods);
		return true;
	}

	@Transactional(readOnly = false)
	public boolean goodsEvaluateAgain(GoodsEvaluate goodsEvaluate,Orders orders,OrderGoods orderGoods){
		GoodsEvaluate ge = goodsEvaluateMapper.findByOrderOrCode(orderGoods.getOrderId(),orderGoods.getGoodsCode());
		//评价相关信息
		ge.setAddtimeAgain(new Date());
		ge.setContentAgain(goodsEvaluate.getContentAgain());
		ge.setImageAgain(goodsEvaluate.getImageAgain());
		//保存评价
		mapper.update(ge);
		//更新订单
		orderGoods.setEvaluateStatus("2");
		orderGoodsMapper.update(orderGoods);
		return true;
	}

}