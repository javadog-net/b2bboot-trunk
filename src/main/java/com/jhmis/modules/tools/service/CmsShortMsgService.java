/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.tools.entity.CmsShortMsg;
import com.jhmis.modules.tools.mapper.CmsShortMsgMapper;

/**
 * 发送短信Service
 * @author tc
 * @version 2019-09-04
 */
@Service
@Transactional(readOnly = true)
public class CmsShortMsgService extends CrudService<CmsShortMsgMapper, CmsShortMsg> {

	public CmsShortMsg get(String id) {
		return super.get(id);
	}
	
	public List<CmsShortMsg> findList(CmsShortMsg cmsShortMsg) {
		return super.findList(cmsShortMsg);
	}
	
	public Page<CmsShortMsg> findPage(Page<CmsShortMsg> page, CmsShortMsg cmsShortMsg) {
		return super.findPage(page, cmsShortMsg);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsShortMsg cmsShortMsg) {
		super.save(cmsShortMsg);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsShortMsg cmsShortMsg) {
		super.delete(cmsShortMsg);
	}
	
}