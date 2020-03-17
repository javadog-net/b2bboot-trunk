/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.service.shopmsgproduct;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.process.entity.shopmsgproduct.ShopMsgProduct;
import com.jhmis.modules.process.mapper.shopmsgproduct.ShopMsgProductMapper;

/**
 * 需求产品子表Service
 * @author hdx
 * @version 2019-09-23
 */
@Service
@Transactional(readOnly = true)
public class ShopMsgProductService extends CrudService<ShopMsgProductMapper, ShopMsgProduct> {

	public ShopMsgProduct get(String id) {
		return super.get(id);
	}
	
	public List<ShopMsgProduct> findList(ShopMsgProduct shopMsgProduct) {
		return super.findList(shopMsgProduct);
	}
	
	public Page<ShopMsgProduct> findPage(Page<ShopMsgProduct> page, ShopMsgProduct shopMsgProduct) {
		return super.findPage(page, shopMsgProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopMsgProduct shopMsgProduct) {
		super.save(shopMsgProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopMsgProduct shopMsgProduct) {
		super.delete(shopMsgProduct);
	}
	
}