/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.service.TreeService;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.modules.sys.entity.AttCat;
import com.jhmis.modules.sys.mapper.AttCatMapper;

/**
 * 附件管理Service
 * @author tity
 * @version 2018-07-06
 */
@Service
@Transactional(readOnly = true)
public class AttCatService extends TreeService<AttCatMapper, AttCat> {

	public AttCat get(String id) {
		return super.get(id);
	}
	
	public List<AttCat> findList(AttCat attCat) {
		if (StringUtils.isNotBlank(attCat.getParentIds())){
			attCat.setParentIds(","+attCat.getParentIds()+",");
		}
		return super.findList(attCat);
	}
	
	@Transactional(readOnly = false)
	public void save(AttCat attCat) {
		super.save(attCat);
	}
	
	@Transactional(readOnly = false)
	public void delete(AttCat attCat) {
		super.delete(attCat);
	}
	
}