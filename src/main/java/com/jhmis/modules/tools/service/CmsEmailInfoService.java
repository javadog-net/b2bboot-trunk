/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.tools.entity.CmsEmailInfo;
import com.jhmis.modules.tools.mapper.CmsEmailInfoMapper;

/**
 * 邮件Service
 * @author tc
 * @version 2019-09-03
 */
@Service
@Transactional(readOnly = true)
public class CmsEmailInfoService extends CrudService<CmsEmailInfoMapper, CmsEmailInfo> {

	@Autowired
	private CmsEmailInfoMapper mapper;
	
	public CmsEmailInfo get(String id) {
		return super.get(id);
	}
	
	public List<CmsEmailInfo> findList(CmsEmailInfo cmsEmailInfo) {
		return super.findList(cmsEmailInfo);
	}
	
	public Page<CmsEmailInfo> findPage(Page<CmsEmailInfo> page, CmsEmailInfo cmsEmailInfo) {
		return super.findPage(page, cmsEmailInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsEmailInfo cmsEmailInfo) {
		super.save(cmsEmailInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsEmailInfo cmsEmailInfo) {
		super.delete(cmsEmailInfo);
	}
	@Transactional(readOnly = false)
	public void updateIsSend(CmsEmailInfo cmsEmailInfo) {
		mapper.updateIsSend(cmsEmailInfo);
	}
	
}