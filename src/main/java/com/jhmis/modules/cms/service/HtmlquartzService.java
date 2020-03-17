/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.Htmlquartz;
import com.jhmis.modules.cms.mapper.HtmlquartzMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 静态化调度Service
 * @author lydia
 * @version 2019-12-13
 */
@Service
@Transactional(readOnly = true)
public class HtmlquartzService extends CrudService<HtmlquartzMapper, Htmlquartz> {

	public Htmlquartz get(String id) {
		return super.get(id);
	}
	
	public List<Htmlquartz> findList(Htmlquartz htmlquartz) {
		return super.findList(htmlquartz);
	}
	
	public Page<Htmlquartz> findPage(Page<Htmlquartz> page, Htmlquartz htmlquartz) {
		return super.findPage(page, htmlquartz);
	}
	
	@Transactional(readOnly = false)
	public void save(Htmlquartz htmlquartz) {
		super.save(htmlquartz);
	}
	
	@Transactional(readOnly = false)
	public void delete(Htmlquartz htmlquartz) {
		super.delete(htmlquartz);
	}
	
}