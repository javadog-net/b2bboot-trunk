/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.wechat.entity.WxFeedback;
import com.jhmis.modules.wechat.mapper.WxFeedbackMapper;
import com.jhmis.modules.wechat.mapper.WxMemberMapper;

/**
 * 反馈信息Service
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly = true)
public class WxFeedbackService extends CrudService<WxFeedbackMapper, WxFeedback> {
	@Autowired
	private WxFeedbackMapper wxFeedbackMapper;
	@Autowired
	private WxMemberMapper wxMemberMapper;
	@Autowired
	PurchaserAccountService purchaserAccountService;
	
	public WxFeedback get(String id) {
		return super.get(id);
	}
	
	public List<WxFeedback> findList(WxFeedback wxFeedback) {
		return super.findList(wxFeedback);
	}
	
	public Page<WxFeedback> findPage(Page<WxFeedback> page, WxFeedback wxFeedback) {
		return super.findPage(page, wxFeedback);
	}
	
	@Transactional(readOnly = false)
	public void save(WxFeedback wxFeedback) {
		super.save(wxFeedback);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxFeedback wxFeedback) {
		super.delete(wxFeedback);
	}
	/**
	 * 根据userId，将反馈信息存入数据库
	 * @param userId
	 * @param content
	 */
	@Transactional(readOnly = false)
	public void add(String userId,String content) {
		WxFeedback wxFeedback = new WxFeedback();
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		wxFeedback.setId(id);
		wxFeedback.setUserId(userId);
		wxFeedback.setUserName(purchaserAccountService.getNicknameById(userId));
		wxFeedback.setContent(content);
		wxFeedback.setCreateTime(new Date());
		wxFeedbackMapper.add(wxFeedback);
	}
}