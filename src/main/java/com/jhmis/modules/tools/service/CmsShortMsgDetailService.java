/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.tools.entity.CmsShortMsgDetail;
import com.jhmis.modules.tools.mapper.CmsShortMsgDetailMapper;

/**
 * 短信履历Service
 * @author tc
 * @version 2019-09-04
 */
@Service
@Transactional(readOnly = true)
public class CmsShortMsgDetailService extends CrudService<CmsShortMsgDetailMapper, CmsShortMsgDetail> {

	public CmsShortMsgDetail get(String id) {
		return super.get(id);
	}
	
	public List<CmsShortMsgDetail> findList(CmsShortMsgDetail cmsShortMsgDetail) {
		return super.findList(cmsShortMsgDetail);
	}
	
	public Page<CmsShortMsgDetail> findPage(Page<CmsShortMsgDetail> page, CmsShortMsgDetail cmsShortMsgDetail) {
		return super.findPage(page, cmsShortMsgDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsShortMsgDetail cmsShortMsgDetail) {
		super.save(cmsShortMsgDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsShortMsgDetail cmsShortMsgDetail) {
		super.delete(cmsShortMsgDetail);
	}

	
}