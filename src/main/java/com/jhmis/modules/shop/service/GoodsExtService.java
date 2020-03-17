/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.GoodsExt;
import com.jhmis.modules.shop.mapper.GoodsExtMapper;

/**
 * 商品扩展资料Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class GoodsExtService extends CrudService<GoodsExtMapper, GoodsExt> {

	public GoodsExt get(String id) {
		return super.get(id);
	}
	
	public List<GoodsExt> findList(GoodsExt goodsExt) {
		return super.findList(goodsExt);
	}
	
	public Page<GoodsExt> findPage(Page<GoodsExt> page, GoodsExt goodsExt) {
		return super.findPage(page, goodsExt);
	}
	
	@Transactional(readOnly = false)
	public void save(GoodsExt goodsExt) {
		super.save(goodsExt);
	}
	
	@Transactional(readOnly = false)
	public void delete(GoodsExt goodsExt) {
		super.delete(goodsExt);
	}

	public void deleteByCode(String code) {
		mapper.deleteByCode(code);
	}
}