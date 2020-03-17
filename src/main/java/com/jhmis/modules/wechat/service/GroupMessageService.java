/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.GroupMessage;
import com.jhmis.modules.wechat.mapper.GroupMessageMapper;

/**
 * 群组聊天信息Service
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly = true)
public class GroupMessageService extends CrudService<GroupMessageMapper, GroupMessage> {

	public GroupMessage get(String id) {
		return super.get(id);
	}
	
	public List<GroupMessage> findList(GroupMessage groupMessage) {
		return super.findList(groupMessage);
	}
	
	public Page<GroupMessage> findPage(Page<GroupMessage> page, GroupMessage groupMessage) {
		return super.findPage(page, groupMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(GroupMessage groupMessage) {
		super.save(groupMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(GroupMessage groupMessage) {
		super.delete(groupMessage);
	}
	
}