/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.old.entity.OldShopProjectProductDetail;
import com.jhmis.modules.old.mapper.OldShopProjectProductDetailMapper;

/**
 * 2Service
 * @author 2
 * @version 2019-12-06
 */
@Service
@Transactional(readOnly = true)
public class OldShopProjectProductDetailService extends CrudService<OldShopProjectProductDetailMapper, OldShopProjectProductDetail> {

	public OldShopProjectProductDetail get(String id) {
		return super.get(id);
	}
	
	public List<OldShopProjectProductDetail> findList(OldShopProjectProductDetail oldShopProjectProductDetail) {
		return super.findList(oldShopProjectProductDetail);
	}
	
	public Page<OldShopProjectProductDetail> findPage(Page<OldShopProjectProductDetail> page, OldShopProjectProductDetail oldShopProjectProductDetail) {
		return super.findPage(page, oldShopProjectProductDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(OldShopProjectProductDetail oldShopProjectProductDetail) {
		super.save(oldShopProjectProductDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(OldShopProjectProductDetail oldShopProjectProductDetail) {
		super.delete(oldShopProjectProductDetail);
	}
	
}