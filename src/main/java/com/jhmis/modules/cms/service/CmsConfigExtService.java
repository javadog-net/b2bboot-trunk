/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.CmsConfigExt;
import com.jhmis.modules.cms.mapper.CmsConfigExtMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 内容管理-水印功能Service
 * @author lydia
 * @version 2019-09-02
 */
@Service
@Transactional(readOnly = true)
public class CmsConfigExtService extends CrudService<CmsConfigExtMapper, CmsConfigExt> {

	public CmsConfigExt get(String id) {
		return super.get(id);
	}
	
	public List<CmsConfigExt> findList(CmsConfigExt cmsConfigExt) {
		return super.findList(cmsConfigExt);
	}
	
	public Page<CmsConfigExt> findPage(Page<CmsConfigExt> page, CmsConfigExt cmsConfigExt) {
		return super.findPage(page, cmsConfigExt);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsConfigExt cmsConfigExt) {
		super.save(cmsConfigExt);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsConfigExt cmsConfigExt) {
		super.delete(cmsConfigExt);
	}
	
}