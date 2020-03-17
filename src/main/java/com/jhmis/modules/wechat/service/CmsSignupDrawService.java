/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.CmsSignupDraw;
import com.jhmis.modules.wechat.mapper.CmsSignupDrawMapper;

/**
 * 参与报名抽奖Service
 * @author tc
 * @version 2019-02-27
 */
@Service
@Transactional(readOnly = true)
public class CmsSignupDrawService extends CrudService<CmsSignupDrawMapper, CmsSignupDraw> {
@Resource
private CmsSignupDrawMapper cmsSignupDrawMapper;
	public CmsSignupDraw get(String id) {
		return super.get(id);
	}
	
	public List<CmsSignupDraw> findList(CmsSignupDraw cmsSignupDraw) {
		return super.findList(cmsSignupDraw);
	}
	
	public Page<CmsSignupDraw> findPage(Page<CmsSignupDraw> page, CmsSignupDraw cmsSignupDraw) {
		return super.findPage(page, cmsSignupDraw);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsSignupDraw cmsSignupDraw) {
		super.save(cmsSignupDraw);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsSignupDraw cmsSignupDraw) {
		super.delete(cmsSignupDraw);
	}
	@Transactional(readOnly = false)
	public int deletebyuserid(String id) {
		return cmsSignupDrawMapper.deletebyuserid(id);
	}

	public CmsSignupDraw findReapte(String actvid, String userid) {
		// TODO Auto-generated method stub
		return cmsSignupDrawMapper.findReapte(actvid,userid);
	}
	
}