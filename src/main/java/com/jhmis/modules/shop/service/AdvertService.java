/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.Advert;
import com.jhmis.modules.shop.mapper.AdvertMapper;

/**
 * 广告管理Service
 * @author hdx
 * @version 2018-08-04
 */
@Service
@Transactional(readOnly = true)
public class AdvertService extends CrudService<AdvertMapper, Advert> {

	public Advert get(String id) {
		return super.get(id);
	}
	
	public List<Advert> findList(Advert advert) {
		return super.findList(advert);
	}
	
	public Page<Advert> findPage(Page<Advert> page, Advert advert) {
		return super.findPage(page, advert);
	}
	
	@Transactional(readOnly = false)
	public void save(Advert advert) {
		super.save(advert);
	}
	
	@Transactional(readOnly = false)
	public void delete(Advert advert) {
		super.delete(advert);
	}
	
}