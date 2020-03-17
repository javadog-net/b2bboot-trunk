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
import com.jhmis.modules.wechat.entity.WxFriendMessage;
import com.jhmis.modules.wechat.mapper.WxFriendMessageMapper;

/**
 * 聊天信息表Service
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly = true)
public class WxFriendMessageService extends CrudService<WxFriendMessageMapper, WxFriendMessage> {
	@Autowired
	WxFriendMessageMapper wxFriendMessageMapper;
	public WxFriendMessage get(String id) {
		return super.get(id);
	}
	
	public List<WxFriendMessage> findList(WxFriendMessage wxFriendMessage) {
		return super.findList(wxFriendMessage);
	}
	
	public Page<WxFriendMessage> findPage(Page<WxFriendMessage> page, WxFriendMessage wxFriendMessage) {
		return super.findPage(page, wxFriendMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(WxFriendMessage wxFriendMessage) {
		super.save(wxFriendMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxFriendMessage wxFriendMessage) {
		super.delete(wxFriendMessage);
	}

	public List<WxFriendMessage> findFriendMsgInfoList(String userId) {
		return mapper.findFriendMsgInfoList(userId);
	}
	@Transactional(readOnly = false)
	public int messageNumber(String userId,String friendId){
		return wxFriendMessageMapper.messageNumber(userId,friendId);
	}
	@Transactional(readOnly = false)
	public int deletebyfromuserid(String id) {
	 return	wxFriendMessageMapper.deletebyfromuserid(id);
	}
	@Transactional(readOnly = false)
	public int deletebytouserid(String id) {
		// TODO Auto-generated method stub
		return wxFriendMessageMapper.deletebytouserid(id);
	}
	@Transactional(readOnly = false)
	public void updateIsRead(String friendId, String userId) {
		// TODO Auto-generated method stub
		wxFriendMessageMapper.updateIsRead(friendId,userId);
	}

	public WxFriendMessage getlastmessage(String userId, String friendId) {
		// TODO Auto-generated method stub
		return wxFriendMessageMapper.getlastmessage(userId,friendId);
	}

	public List<WxFriendMessage> findToUserIdIsMe(String id) {
		// TODO Auto-generated method stub
		return wxFriendMessageMapper.findToUserIdIsMe(id);
	}
	
}