/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.DirectMsgProduct;
import com.jhmis.modules.shop.mapper.DirectMsgProductMapper;

/**
 * 直采需求商品Service
 * @author hdx
 * @version 2019-04-03
 */
@Service
@Transactional(readOnly = true)
public class DirectMsgProductService extends CrudService<DirectMsgProductMapper, DirectMsgProduct> {

	public DirectMsgProduct get(String id) {
		return super.get(id);
	}
	
	public List<DirectMsgProduct> findList(DirectMsgProduct directMsgProduct) {
		return super.findList(directMsgProduct);
	}
	
	public Page<DirectMsgProduct> findPage(Page<DirectMsgProduct> page, DirectMsgProduct directMsgProduct) {
		return super.findPage(page, directMsgProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(DirectMsgProduct directMsgProduct) {
		super.save(directMsgProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(DirectMsgProduct directMsgProduct) {
		super.delete(directMsgProduct);
	}
	
}