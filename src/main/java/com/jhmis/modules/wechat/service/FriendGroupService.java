/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import com.jhmis.modules.wechat.entity.WxFriendGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.mapper.FriendGroupMapper;

/**
 * 分组表Service
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly = true)
public class FriendGroupService extends CrudService<FriendGroupMapper, WxFriendGroup> {

	public WxFriendGroup get(String id) {
		return super.get(id);
	}
	
	public List<WxFriendGroup> findList(WxFriendGroup wxFriendGroup) {
		return super.findList(wxFriendGroup);
	}
	
	public Page<WxFriendGroup> findPage(Page<WxFriendGroup> page, WxFriendGroup wxFriendGroup) {
		return super.findPage(page, wxFriendGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(WxFriendGroup wxFriendGroup) {
		super.save(wxFriendGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxFriendGroup wxFriendGroup) {
		super.delete(wxFriendGroup);
	}
	
}