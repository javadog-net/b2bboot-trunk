/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.old.entity.OldShopProjectProduct;
import com.jhmis.modules.old.mapper.OldShopProjectProductMapper;

/**
 * 1Service
 * @author 1
 * @version 2019-12-06
 */
@Service
@Transactional(readOnly = true)
public class OldShopProjectProductService extends CrudService<OldShopProjectProductMapper, OldShopProjectProduct> {

	public OldShopProjectProduct get(String id) {
		return super.get(id);
	}
	
	public List<OldShopProjectProduct> findList(OldShopProjectProduct oldShopProjectProduct) {
		return super.findList(oldShopProjectProduct);
	}
	
	public Page<OldShopProjectProduct> findPage(Page<OldShopProjectProduct> page, OldShopProjectProduct oldShopProjectProduct) {
		return super.findPage(page, oldShopProjectProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(OldShopProjectProduct oldShopProjectProduct) {
		super.save(oldShopProjectProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(OldShopProjectProduct oldShopProjectProduct) {
		super.delete(oldShopProjectProduct);
	}
	
}