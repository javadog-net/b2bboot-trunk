/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.service.dispatcher;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.process.entity.dispatcher.CmsOperlogs;
import com.jhmis.modules.process.mapper.dispatcher.CmsOperlogsMapper;

/**
 * 抢派单操作日志Service
 * @author tc
 * @version 2019-09-19
 */
@Service
@Transactional(readOnly = true)
public class CmsOperlogsService extends CrudService<CmsOperlogsMapper, CmsOperlogs> {

	public CmsOperlogs get(String id) {
		return super.get(id);
	}
	
	public List<CmsOperlogs> findList(CmsOperlogs cmsOperlogs) {
		return super.findList(cmsOperlogs);
	}
	
	public Page<CmsOperlogs> findPage(Page<CmsOperlogs> page, CmsOperlogs cmsOperlogs) {
		return super.findPage(page, cmsOperlogs);
	}
	
	@Transactional(readOnly = false)
	public void save(CmsOperlogs cmsOperlogs) {
		super.save(cmsOperlogs);
	}
	
	@Transactional(readOnly = false)
	public void delete(CmsOperlogs cmsOperlogs) {
		super.delete(cmsOperlogs);
	}
	
}