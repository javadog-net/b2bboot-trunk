/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.sys.entity.GmInfo;
import com.jhmis.modules.sys.mapper.GmInfoMapper;

/**
 * 工贸信息Service
 * @author tity
 * @version 2018-07-22
 */
@Service
@Transactional(readOnly = true)
public class GmInfoService extends CrudService<GmInfoMapper, GmInfo> {
@Autowired
private GmInfoMapper mapper;
	public GmInfo get(String id) {
		return super.get(id);
	}
	
	public List<GmInfo> findList(GmInfo gmInfo) {
		return super.findList(gmInfo);
	}
	
	public Page<GmInfo> findPage(Page<GmInfo> page, GmInfo gmInfo) {
		return super.findPage(page, gmInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(GmInfo gmInfo) {
		
		super.save(gmInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(GmInfo gmInfo) {
		super.delete(gmInfo);
	}

	public GmInfo findByBranchCode(String gmId) {
		// TODO Auto-generated method stub
		return mapper.findByBranchCode(gmId);
	}

	
	
}