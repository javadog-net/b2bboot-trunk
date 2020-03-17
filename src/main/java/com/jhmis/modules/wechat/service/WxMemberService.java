/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.WxMember;
import com.jhmis.modules.wechat.mapper.WxMemberMapper;

/**
 * 微信会员信息表Service
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly = true)
public class WxMemberService extends CrudService<WxMemberMapper, WxMember> {
	@Autowired
 private WxMemberMapper wxmembermapper;
	public WxMember get(String id) {
		return super.get(id);
	}
	
	public List<WxMember> findList(WxMember wxMember) {
		return super.findList(wxMember);
	}
	
	public Page<WxMember> findPage(Page<WxMember> page, WxMember wxMember) {
		return super.findPage(page, wxMember);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMember wxMember) {
		wxMember.setLoginLastTime(new Date());
		super.save(wxMember);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMember wxMember) {
		super.delete(wxMember);
	}

	@Transactional(readOnly = false)
	public void update(WxMember wxMember) {
		wxMember.setLoginLastTime(new Date());
		mapper.update(wxMember);
	}
	@Transactional(readOnly = false)
	public WxMember findbinding(String id, String openid) {
		return wxmembermapper.findbinding(id,openid);
	}
	/**
	 * 根据企业购用户id，获取用户的微信头像
	 * @param userId
	 * @return 头像存储的url
	 */
	public String findAvatarurlByUserId(String userId){
		if(StringUtils.isBlank(wxmembermapper.findAvatarurlByUserId(userId))){
			return "";
		}else{
			return wxmembermapper.findAvatarurlByUserId(userId);
		}
	}
	/**
	 * 根据企业购用户id,获取用户昵称
	 * @param userId
	 * @return 用户昵称
	 */
	public String findNicknameByUserId(String userId){
		return wxmembermapper.findNicknameByUserId(userId);
	}
	
}