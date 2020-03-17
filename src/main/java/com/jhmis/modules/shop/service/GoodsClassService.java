/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import com.jhmis.modules.shop.mapper.GoodsClassPropertiesMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.GoodsClass;
import com.jhmis.modules.shop.mapper.GoodsClassMapper;

import javax.annotation.Resource;

/**
 * 商品类型管理Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class GoodsClassService extends CrudService<GoodsClassMapper, GoodsClass> {

	@Resource
	GoodsClassPropertiesMapper goodsClassPropertiesMapper;

	public GoodsClass get(String id) {
		return super.get(id);
	}
	
	public List<GoodsClass> findList(GoodsClass goodsClass) {
		return super.findList(goodsClass);
	}
	
	public Page<GoodsClass> findPage(Page<GoodsClass> page, GoodsClass goodsClass) {
		return super.findPage(page, goodsClass);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsClass goodsClass) {
		super.save(goodsClass);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsClass goodsClass) {
		//先删除其子类
		goodsClassPropertiesMapper.deleteByClassId(goodsClass.getId());
		super.delete(goodsClass);
	}

}