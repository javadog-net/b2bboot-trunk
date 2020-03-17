/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.GoodsProperties;
import com.jhmis.modules.shop.mapper.GoodsPropertiesMapper;

/**
 * 商品属性Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class GoodsPropertiesService extends CrudService<GoodsPropertiesMapper, GoodsProperties> {

	public GoodsProperties get(String id) {
		return super.get(id);
	}
	
	public List<GoodsProperties> findList(GoodsProperties goodsProperties) {
		return super.findList(goodsProperties);
	}
	
	public Page<GoodsProperties> findPage(Page<GoodsProperties> page, GoodsProperties goodsProperties) {
		return super.findPage(page, goodsProperties);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsProperties goodsProperties) {
		super.save(goodsProperties);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsProperties goodsProperties) {
		super.delete(goodsProperties);
	}
	
}