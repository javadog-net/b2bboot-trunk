/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.CmsModelField;
import com.jhmis.modules.cms.mapper.CmsModelFieldMapper;

/**
 * 模块字段管理Service
 * @author lydia
 * @version 2019-08-30
 */
@Service
@Transactional(readOnly = true)
public class CmsModelFieldService extends CrudService<CmsModelFieldMapper, CmsModelField> {

	public CmsModelField get(String id) {
		return super.get(id);
	}
	
	public List<CmsModelField> findList(CmsModelField cmsModelField) {
		return super.findList(cmsModelField);
	}
	
	public Page<CmsModelField> findPage(Page<CmsModelField> page, CmsModelField cmsModelField) {
		return super.findPage(page, cmsModelField);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsModelField cmsModelField) {
		super.save(cmsModelField);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsModelField cmsModelField) {
		super.delete(cmsModelField);
	}
	
}