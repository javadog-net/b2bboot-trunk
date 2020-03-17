/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.WxGroup;
import com.jhmis.modules.wechat.mapper.WxGroupMapper;

/**
 * 群组表Service
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly = true)
public class WxGroupService extends CrudService<WxGroupMapper, WxGroup> {
	@Autowired
	private WxGroupMapper wxGroupMapper;

	public WxGroup get(String id) {
		return super.get(id);
	}
	
	public List<WxGroup> findList(WxGroup wxGroup) {
		return super.findList(wxGroup);
	}
	
	public Page<WxGroup> findPage(Page<WxGroup> page, WxGroup wxGroup) {
		return super.findPage(page, wxGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(WxGroup wxGroup) {
		super.save(wxGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxGroup wxGroup) {
		super.delete(wxGroup);
	}
	@Transactional(readOnly = false)
	public List<WxGroup> findrepeatgroup(String id, String industryname) {
		return wxGroupMapper.findrepeatgroup(id,industryname);
	}
	@Transactional(readOnly = false)
	public WxGroup findbysource(String industryname) {
		return wxGroupMapper.findbysource(industryname);
	}
	
}