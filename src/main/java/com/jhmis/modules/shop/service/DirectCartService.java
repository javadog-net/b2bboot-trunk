/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.DirectCart;
import com.jhmis.modules.shop.mapper.DirectCartMapper;

/**
 * 直采购物车Service
 * @author hdx
 * @version 2019-03-27
 */
@Service
@Transactional(readOnly = true)
public class DirectCartService extends CrudService<DirectCartMapper, DirectCart> {

	@Autowired
	DirectCartMapper directCartMapper;

	public DirectCart get(String id) {
		return super.get(id);
	}
	
	public List<DirectCart> findList(DirectCart directCart) {
		return super.findList(directCart);
	}
	
	public Page<DirectCart> findPage(Page<DirectCart> page, DirectCart directCart) {
		return super.findPage(page, directCart);
	}
	
	@Transactional(readOnly = false)
	public void save(DirectCart directCart) {
		super.save(directCart);
	}
	
	@Transactional(readOnly = false)
	public void delete(DirectCart directCart) {
		super.delete(directCart);
	}

	/**
	 * 查询购物车中产品件数
	 * @param cart
	 * @return
	 */
	public int getCartCount(DirectCart cart){
		return directCartMapper.getCartCount(cart);
	}


	public List<DirectCart> getGroupProduct(String dealerId){
		return directCartMapper.getGroupProduct(dealerId);
	}
	
}