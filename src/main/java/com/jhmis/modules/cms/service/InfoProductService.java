/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.InfoProduct;
import com.jhmis.modules.cms.mapper.InfoProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文章关联产品Service
 * @author lydia
 * @version 2019-10-14
 */
@Service
@Transactional(readOnly = true)
public class InfoProductService extends CrudService<InfoProductMapper, InfoProduct> {

	public InfoProduct get(String id) {
		return super.get(id);
	}
	
	public List<InfoProduct> findList(InfoProduct infoProduct) {
		return  super.findList(infoProduct);
	}
	
	public Page<InfoProduct> findPage(Page<InfoProduct> page, InfoProduct infoProduct) {
		return super.findPage(page, infoProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(InfoProduct infoProduct) {
		super.save(infoProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(InfoProduct infoProduct) {
		super.delete(infoProduct);
	}
	
}