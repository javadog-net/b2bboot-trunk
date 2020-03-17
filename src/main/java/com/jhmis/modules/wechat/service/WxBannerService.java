/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.BaseService;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxBanner;
import com.jhmis.modules.wechat.mapper.WxBannerMapper;

/**
 * bannerService
 * @author abc
 * @version 2019-01-25
 */
@Service
@Transactional(readOnly = true)
public class WxBannerService extends BaseService {
	@Autowired
	WxBannerMapper wxBannerMapper;
	
	public WxBanner get(String id) {
		return wxBannerMapper.get(id);
	}
	
	public List<WxBanner> findList(WxBanner wxBanner) {
		return wxBannerMapper.findList(wxBanner);
	}
	
	public Page<WxBanner> findPage(Page<WxBanner> page, WxBanner wxBanner) {		
		dataRuleFilter(wxBanner);
		wxBanner.setPage(page);
		page.setList(wxBannerMapper.findList(wxBanner));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(WxBanner wxBanner) {
		User currentUser = UserUtils.getUser();
		if(wxBanner!=null){
			wxBanner.setCreateTime(new Date());
			wxBanner.setCreatePerson(currentUser.getName());
			if(StringUtils.isBlank(wxBanner.getId())){
				wxBanner.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				wxBannerMapper.insert(wxBanner);
			}else{
				wxBannerMapper.update(wxBanner);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WxBanner wxBanner) {
		wxBannerMapper.delete(wxBanner);
	}
	
}