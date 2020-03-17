/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.GoodsRecommend;
import com.jhmis.modules.shop.mapper.GoodsRecommendMapper;

/**
 * 商品推荐Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class GoodsRecommendService extends CrudService<GoodsRecommendMapper, GoodsRecommend> {

	public GoodsRecommend get(String id) {
		return super.get(id);
	}
	
	public List<GoodsRecommend> findList(GoodsRecommend goodsRecommend) {
		return super.findList(goodsRecommend);
	}
	
	public Page<GoodsRecommend> findPage(Page<GoodsRecommend> page, GoodsRecommend goodsRecommend) {
		return super.findPage(page, goodsRecommend);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsRecommend goodsRecommend) {
		super.save(goodsRecommend);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsRecommend goodsRecommend) {
		super.delete(goodsRecommend);
	}
	
}