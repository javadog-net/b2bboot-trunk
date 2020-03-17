/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.cms.entity.InfoImg;
import com.jhmis.modules.cms.mapper.InfoImgMapper;

/**
 * 内容图片集Service
 * @author lydia
 * @version 2019-12-11
 */
@Service
@Transactional(readOnly = true)
public class InfoImgService extends CrudService<InfoImgMapper, InfoImg> {

	public InfoImg get(String id) {
		return super.get(id);
	}
	
	public List<InfoImg> findList(InfoImg infoImg) {
		return super.findList(infoImg);
	}
	
	public Page<InfoImg> findPage(Page<InfoImg> page, InfoImg infoImg) {
		return super.findPage(page, infoImg);
	}
	
	@Transactional(readOnly = false)
	public void save(InfoImg infoImg) {
		super.save(infoImg);
	}
	
	@Transactional(readOnly = false)
	public void delete(InfoImg infoImg) {
		super.delete(infoImg);
	}
	
}