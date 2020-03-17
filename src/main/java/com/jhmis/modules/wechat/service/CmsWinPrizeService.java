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
import com.jhmis.modules.wechat.entity.CmsWinPrize;
import com.jhmis.modules.wechat.mapper.CmsWinPrizeMapper;

/**
 * 中奖表Service
 * @author tc
 * @version 2019-02-27
 */
@Service
@Transactional(readOnly = true)
public class CmsWinPrizeService extends CrudService<CmsWinPrizeMapper, CmsWinPrize> {

	@Autowired
	private CmsWinPrizeMapper winPrizemapper;
	public CmsWinPrize get(String id) {
		return super.get(id);
	}
	
	public List<CmsWinPrize> findList(CmsWinPrize cmsWinPrize) {
		return super.findList(cmsWinPrize);
	}
	
	public Page<CmsWinPrize> findPage(Page<CmsWinPrize> page, CmsWinPrize cmsWinPrize) {
		return super.findPage(page, cmsWinPrize);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsWinPrize cmsWinPrize) {
		super.save(cmsWinPrize);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsWinPrize cmsWinPrize) {
		super.delete(cmsWinPrize);
	}
	
	@Transactional(readOnly = false)
	public List<CmsWinPrize> findDrawList(String actvid,String phone,String companyname,String username) {
		return	winPrizemapper.findDrawList(actvid,phone,companyname,username);
	
	}
	@Transactional(readOnly = false)
	public CmsWinPrize getByid(String actvid,String userId) {
		
		return	winPrizemapper.getByid(actvid,userId);
		
	}
	@Transactional(readOnly = false)
	public int deletebyuserid(String id) {
		 return winPrizemapper.deletebyuserid(id);
	}
	
}