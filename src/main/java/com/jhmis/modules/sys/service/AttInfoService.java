/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.sys.entity.AttInfo;
import com.jhmis.modules.sys.mapper.AttInfoMapper;

/**
 * 附件信息Service
 * @author tity
 * @version 2018-07-06
 */
@Service
@Transactional(readOnly = true)
public class AttInfoService extends CrudService<AttInfoMapper, AttInfo> {

	public AttInfo get(String id) {
		return super.get(id);
	}
	
	public List<AttInfo> findList(AttInfo attInfo) {
		return super.findList(attInfo);
	}
	
	public Page<AttInfo> findPage(Page<AttInfo> page, AttInfo attInfo) {
		return super.findPage(page, attInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(AttInfo attInfo) {
		super.save(attInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(AttInfo attInfo) {
		super.delete(attInfo);
	}
	
}