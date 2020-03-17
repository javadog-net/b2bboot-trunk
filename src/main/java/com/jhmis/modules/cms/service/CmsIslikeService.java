/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.CmsIslike;
import com.jhmis.modules.cms.mapper.CmsIslikeMapper;

/**
 * info信息  是否喜欢 Service
 * @author tc
 * @version 2019-11-07
 */
@Service
@Transactional(readOnly = true)
public class CmsIslikeService extends CrudService<CmsIslikeMapper, CmsIslike> {

	public CmsIslike get(String id) {
		return super.get(id);
	}
	
	public List<CmsIslike> findList(CmsIslike cmsIslike) {
		return super.findList(cmsIslike);
	}
	
	public Page<CmsIslike> findPage(Page<CmsIslike> page, CmsIslike cmsIslike) {
		return super.findPage(page, cmsIslike);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsIslike cmsIslike) {
		super.save(cmsIslike);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsIslike cmsIslike) {
		super.delete(cmsIslike);
	}

    public Integer selectCountById(String id) {
		return mapper.selectCountById(id);
    }
}