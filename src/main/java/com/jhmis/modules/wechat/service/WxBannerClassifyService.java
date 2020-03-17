/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.WxBannerClassify;
import com.jhmis.modules.wechat.mapper.WxBannerClassifyMapper;

/**
 * banner分类表Service
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly = true)
public class WxBannerClassifyService extends CrudService<WxBannerClassifyMapper, WxBannerClassify> {

	public WxBannerClassify get(String id) {
		return super.get(id);
	}
	
	public List<WxBannerClassify> findList(WxBannerClassify wxBannerClassify) {
		return super.findList(wxBannerClassify);
	}
	
	public Page<WxBannerClassify> findPage(Page<WxBannerClassify> page, WxBannerClassify wxBannerClassify) {
		return super.findPage(page, wxBannerClassify);
	}
	
	@Transactional(readOnly = false)
	public void save(WxBannerClassify wxBannerClassify) {
		super.save(wxBannerClassify);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxBannerClassify wxBannerClassify) {
		super.delete(wxBannerClassify);
	}
	
}