/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.euc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.euc.entity.EucLog;
import com.jhmis.modules.euc.mapper.EucLogMapper;

/**
 * euc_log日志Service
 * @author hdx
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class EucLogService extends CrudService<EucLogMapper, EucLog> {

	public EucLog get(String id) {
		return super.get(id);
	}
	
	public List<EucLog> findList(EucLog eucLog) {
		return super.findList(eucLog);
	}
	
	public Page<EucLog> findPage(Page<EucLog> page, EucLog eucLog) {
		return super.findPage(page, eucLog);
	}
	
	@Transactional(readOnly = false)
	public void save(EucLog eucLog) {
		super.save(eucLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(EucLog eucLog) {
		super.delete(eucLog);
	}
	
}