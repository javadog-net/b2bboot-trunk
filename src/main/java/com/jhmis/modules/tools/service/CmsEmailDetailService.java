/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.tools.entity.CmsEmailDetail;
import com.jhmis.modules.tools.mapper.CmsEmailDetailMapper;

/**
 * 邮件履历Service
 * @author tc
 * @version 2019-09-04
 */
@Service
@Transactional(readOnly = true)
public class CmsEmailDetailService extends CrudService<CmsEmailDetailMapper, CmsEmailDetail> {

	public CmsEmailDetail get(String id) {
		return super.get(id);
	}
	
	public List<CmsEmailDetail> findList(CmsEmailDetail cmsEmailDetail) {
		return super.findList(cmsEmailDetail);
	}
	
	public Page<CmsEmailDetail> findPage(Page<CmsEmailDetail> page, CmsEmailDetail cmsEmailDetail) {
		return super.findPage(page, cmsEmailDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsEmailDetail cmsEmailDetail) {
		super.save(cmsEmailDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsEmailDetail cmsEmailDetail) {
		super.delete(cmsEmailDetail);
	}
	
}