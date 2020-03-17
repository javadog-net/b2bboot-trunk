/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.BaseService;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.Design;
import com.jhmis.modules.wechat.mapper.DesignMapper;

/**
 * 免费设计Service
 * @author abc
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly = true)
public class DesignService extends BaseService {
	@Autowired
	DesignMapper designMapper;
	
	public Design get(String id) {
		return designMapper.get(id);
	}
	
	public List<Design> findList(Design design) {
		return designMapper.findList(design);
	}
	
	public Page<Design> findPage(Page<Design> page, Design design) {
		dataRuleFilter(design);
		design.setPage(page);
		page.setList(designMapper.findList(design));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(Design design) {
		User currentUser = UserUtils.getUser();
		if(design!=null){
			if(StringUtils.isBlank(design.getId())){
				design.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				design.setTime(new Date());
				designMapper.insert(design);
			}else{
				design.setReplyTime(new Date());
				design.setReplyPerson(currentUser.getName());
				designMapper.update(design);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Design design) {
		designMapper.delete(design);
	}
	
}