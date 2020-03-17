/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.mapper.purchaser.PurchaserAccountMapper;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.wechat.entity.PersonalPortrait;
import com.jhmis.modules.wechat.entity.WxFriend;
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.mapper.WxGroupUserMapper;


/**
 * 群组成员Service
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly = true)
public class WxGroupUserService extends CrudService<WxGroupUserMapper, WxGroupUser> {
@Autowired
WxGroupUserMapper wxGroupUserMapper;
@Autowired
WxMemberService wxMemberService;
@Autowired
PurchaserAccountMapper purchaserAccountMapper;
@Autowired
PurchaserAccountService purchaserAccountService;
@Autowired
private WxFriendService wxFriendService;

	public WxGroupUser get(String id) {
		return super.get(id);
	}
	
	public List<WxGroupUser> findList(WxGroupUser wxGroupUser) {
		return super.findList(wxGroupUser);
	}
	
	public Page<WxGroupUser> findPage(Page<WxGroupUser> page, WxGroupUser wxGroupUser) {
		return super.findPage(page, wxGroupUser);
	}
	
	@Transactional(readOnly = false)
	public void save(WxGroupUser wxGroupUser) {
		super.save(wxGroupUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxGroupUser wxGroupUser) {
		super.delete(wxGroupUser);
	}

	@Transactional(readOnly = false)
	public List<WxGroupUser> getGroupList(String userId) {
		return mapper.getGroupList(userId);
	}
	@Transactional(readOnly = false)
	public List<WxGroupUser> getIndustryCircle(String userId) {
		return mapper.getIndustryCircle(userId);
	}


	@Transactional(readOnly = false)
	public List<WxGroupUser> getUserGroup(String userId) {
		return mapper.getUserGroup(userId);
	}

	@Transactional(readOnly = false)
	public List<WxGroupUser> findrepeatgroup(String id) {
		return wxGroupUserMapper.findrepeatgroup(id);
	}
	@Transactional(readOnly = false)
	public void deletebyidandgroupid(String id, String groupid2) {
		wxGroupUserMapper.deletebyidandgroupid(id,groupid2);
	}
	/**
	 * 获取群成员
	 * @param groupId 群组id
	 * @return List<PersonalPortrait>
	 */
	public List<PersonalPortrait> getGroupUsers(String id ,String groupId){		
		List<PersonalPortrait> personalsList = new ArrayList<PersonalPortrait>();
		List<String> groups = wxGroupUserMapper.getGroupUsers(groupId);
		if(groups == null || groups.size()==0){
			return null;
		}
		for(String userId : groups){
			PersonalPortrait personal = new PersonalPortrait(userId);
			personal.setImgUrl(purchaserAccountService.getAvatarById(userId));
			WxFriend wxFriend = new WxFriend();
	        wxFriend.setUserId(id);
	        wxFriend.setTypeId("1");
	        wxFriend.setFriendId(userId);
	        List<WxFriend> wxFriendList= wxFriendService.findList(wxFriend);
	        if(wxFriendList==null || wxFriendList.size()==0){
	        	personal.setNickName(purchaserAccountService.getNicknameById(userId));
	        }else{
	        	personal.setNickName(wxFriendList.get(0).getNickName());
	        }
			PurchaserAccount pa = purchaserAccountMapper.get(userId);
			if(pa!=null){
				personal.setUserMobile(pa.getMobile());
				if(StringUtils.isBlank(personal.getNickName())){
	        		personal.setNickName(pa.getRealName());
	        	}
			}else{
				//personal.setUserMobile("用户不存在");
				continue;
			}
			personalsList.add(personal);	
		}
				
		return personalsList;
	
	}
	@Transactional(readOnly = false)
	public int deletebyuserid(String id) {
	 return	wxGroupUserMapper.deletebyuserid(id);
	}

	@Transactional(readOnly = false)
	public void updateIsRead(String groupId,String state,String userId) {
		// TODO Auto-generated method stub
		wxGroupUserMapper.updateIsRead(groupId,state,userId);
	}
	@Transactional(readOnly = false)
	public void updateIsReadByUidAndGid(String groupId, String id) {
		// TODO Auto-generated method stub
		wxGroupUserMapper.updateIsReadByUidAndGid(groupId,id);
	}

	public List<WxGroupUser> getIsReadGroup(String id) {
		// TODO Auto-generated method stub
		return wxGroupUserMapper.getIsReadGroup(id);
	}

	public List<WxGroupUser> findGeTuiUser(String groupId, String userId) {
		// TODO Auto-generated method stub
		return wxGroupUserMapper.findGeTuiUser(groupId,userId);
	}

	public WxGroupUser findIsOrAdmin(String groupId, String userId) {
		// TODO Auto-generated method stub
		return wxGroupUserMapper.findIsOrAdmin(groupId,userId);
	}
	@Transactional(readOnly = false)
	public void deleteGroupUser(String groupId, String userId) {
		// TODO Auto-generated method stub
		wxGroupUserMapper.deleteGroupUser(groupId,userId);
	}

	
}