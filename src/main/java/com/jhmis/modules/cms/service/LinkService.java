/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.Link;
import com.jhmis.modules.cms.mapper.LinkMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 链接管理Service
 * @author lydia
 * @version 2019-09-06
 */
@Service
@Transactional(readOnly = true)
public class LinkService extends CrudService<LinkMapper, Link> {

	public Link get(String id) {
		return super.get(id);
	}
	
	public List<Link> findList(Link link) {
		return super.findList(link);
	}
	
	public Page<Link> findPage(Page<Link> page, Link link) {
		return super.findPage(page, link);
	}
	
	@Transactional(readOnly = false)
	public void save(Link link) {
		super.save(link);
	}
	
	@Transactional(readOnly = false)
	public void delete(Link link) {
		super.delete(link);
	}
	
}