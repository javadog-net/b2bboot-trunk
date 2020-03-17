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
import com.jhmis.modules.wechat.entity.WxGroupMessage;
import com.jhmis.modules.wechat.mapper.WxGroupMessageMapper;

/**
 * 群聊信息Service
 * @author hdx
 * @version 2018-12-16
 */
@Service
@Transactional(readOnly = true)
public class WxGroupMessageService extends CrudService<WxGroupMessageMapper, WxGroupMessage> {
	@Autowired
	WxGroupMessageMapper wxGroupMessageMapper;
	
	public WxGroupMessage get(String id) {
		return super.get(id);
	}
	
	public List<WxGroupMessage> findList(WxGroupMessage wxGroupMessage) {
		return super.findList(wxGroupMessage);
	}
	
	public Page<WxGroupMessage> findPage(Page<WxGroupMessage> page, WxGroupMessage wxGroupMessage) {
		return super.findPage(page, wxGroupMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(WxGroupMessage wxGroupMessage) {
		super.save(wxGroupMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxGroupMessage wxGroupMessage) {
		super.delete(wxGroupMessage);
	}
	
	public int messageNumber(String groupId){
		return wxGroupMessageMapper.messageNumber(groupId);
	}
	
}