/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.Cart;
import com.jhmis.modules.shop.mapper.CartMapper;

/**
 * 购物车Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class CartService extends CrudService<CartMapper, Cart> {

	public Cart get(String id) {
		return super.get(id);
	}
	
	public List<Cart> findList(Cart cart) {
		return super.findList(cart);
	}
	
	public Page<Cart> findPage(Page<Cart> page, Cart cart) {
		return super.findPage(page, cart);
	}
	
	@Transactional(readOnly = false)
	public void save(Cart cart) {
		super.save(cart);
	}
	
	@Transactional(readOnly = false)
	public void delete(Cart cart) {
		super.delete(cart);
	}

	/**
	 * 查询购物车中产品件数
	 * @param cart
	 * @return
	 */
	public int getCartCount(Cart cart){
		return mapper.getCartCount(cart);
	}
}