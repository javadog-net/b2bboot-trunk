/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.Banner;
import com.jhmis.modules.shop.mapper.BannerMapper;

/**
 * 横幅管理Service
 * @author hdx
 * @version 2018-08-18
 */
@Service
@Transactional(readOnly = true)
public class BannerService extends CrudService<BannerMapper, Banner> {

	public Banner get(String id) {
		return super.get(id);
	}
	
	public List<Banner> findList(Banner banner) {
		return super.findList(banner);
	}
	
	public Page<Banner> findPage(Page<Banner> page, Banner banner) {
		return super.findPage(page, banner);
	}
	
	@Transactional(readOnly = false)
	public void save(Banner banner) {
		super.save(banner);
	}
	
	@Transactional(readOnly = false)
	public void delete(Banner banner) {
		super.delete(banner);
	}
	
}