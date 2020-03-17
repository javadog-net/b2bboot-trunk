/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.old.entity.OldShopProjectInfo;
import com.jhmis.modules.old.mapper.OldShopProjectInfoMapper;

/**
 * projectService
 * @author hdx
 * @version 2019-12-06
 */
@Service
@Transactional(readOnly = true)
public class OldShopProjectInfoService extends CrudService<OldShopProjectInfoMapper, OldShopProjectInfo> {

	public OldShopProjectInfo get(String id) {
		return super.get(id);
	}
	
	public List<OldShopProjectInfo> findList(OldShopProjectInfo oldShopProjectInfo) {
		return super.findList(oldShopProjectInfo);
	}
	
	public Page<OldShopProjectInfo> findPage(Page<OldShopProjectInfo> page, OldShopProjectInfo oldShopProjectInfo) {
		return super.findPage(page, oldShopProjectInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(OldShopProjectInfo oldShopProjectInfo) {
		super.save(oldShopProjectInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(OldShopProjectInfo oldShopProjectInfo) {
		super.delete(oldShopProjectInfo);
	}
	
}