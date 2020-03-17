/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.CmsActivity;
import com.jhmis.modules.wechat.mapper.CmsActivityMapper;

/**
 * 活动表操作Service
 * @author tc
 * @version 2019-02-27
 */
@Service
@Transactional(readOnly = true)
public class CmsActivityService extends CrudService<CmsActivityMapper, CmsActivity> {

	@Autowired
	private CmsActivityMapper mapper;
	
	
	public CmsActivity get(String id) {
		return super.get(id);
	}
	
	public List<CmsActivity> findList(CmsActivity cmsActivity) {
		return super.findList(cmsActivity);
	}
	
	public Page<CmsActivity> findPage(Page<CmsActivity> page, CmsActivity cmsActivity) {
		return super.findPage(page, cmsActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsActivity cmsActivity) {
		super.save(cmsActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsActivity cmsActivity) {
		super.delete(cmsActivity);
	}
	@Transactional(readOnly = false)
	public void updateActivityStatus(String actvId, String tab) {

        mapper.updateActivityStatus(actvId,tab);
	}
	
}