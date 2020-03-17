/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.Date;
import java.util.List;

import com.jhmis.api.store.ApiStoreGoodsController;
import com.jhmis.modules.wechat.entity.WxGroup;
import com.jhmis.modules.wechat.entity.WxGroupUser;
import com.jhmis.modules.wechat.entity.WxMeeting;
import com.jhmis.modules.wechat.mapper.WxGroupMapper;
import com.jhmis.modules.wechat.mapper.WxGroupUserMapper;
import com.jhmis.modules.wechat.mapper.WxMeetingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.WxMeetingSignup;
import com.jhmis.modules.wechat.mapper.WxMeetingSignupMapper;

/**
 * 会议报名表Service
 * 
 * @author lvyangzhuo
 * @version 2018-11-26
 */
@Service
@Transactional(readOnly = true)
public class WxMeetingSignupService extends CrudService<WxMeetingSignupMapper, WxMeetingSignup> {

	protected Logger logger = LoggerFactory.getLogger(WxMeetingSignupService.class);

	@Autowired
	private WxMeetingSignupMapper wxmeetingsignupmapper;

	@Autowired
	private WxGroupUserMapper wxGroupUserMapper;

	@Autowired
	private WxGroupMapper wxGroupMapper;

	@Autowired
	private WxMeetingMapper wxMeetingMapper;

	public WxMeetingSignup get(String id) {
		return super.get(id);
	}

	public List<WxMeetingSignup> findList(WxMeetingSignup wxMeetingSignup) {
		return wxmeetingsignupmapper.findList(wxMeetingSignup);
	}

	public List<WxMeetingSignup> findListVo(WxMeetingSignup wxMeetingSignup) {
		return mapper.findListVo(wxMeetingSignup);
	}

	public Page<WxMeetingSignup> findPage(Page<WxMeetingSignup> page, WxMeetingSignup wxMeetingSignup) {
		return super.findPage(page, wxMeetingSignup);
	}

	@Transactional(readOnly = false)
	public void save(WxMeetingSignup wxMeetingSignup) {
		super.save(wxMeetingSignup);
	}

	@Transactional(readOnly = false)
	public void delete(WxMeetingSignup wxMeetingSignup) {
		super.delete(wxMeetingSignup);
	}
	
	@Transactional(readOnly = false)
	public String signup(WxMeetingSignup wxMeetingSignup) {
		if(wxMeetingSignup.getSignStatus().equals("1") || wxMeetingSignup.getSignStatus().equals("4")){
			wxMeetingSignup.setSignStatus("3");
			super.save(wxMeetingSignup);
			return "1";
		}
	return "0";
	}
	

	@Transactional(readOnly = false)
	public void updatemeetingstatus(WxMeetingSignup wxMeetingSignup) {
		mapper.updatemeetingstatus(wxMeetingSignup);
	}

	@Transactional(readOnly = false)
	public List<WxMeetingSignup> findmeetingsignupstatus(String signstatus, String userid) {
		return wxmeetingsignupmapper.findmeetingsignupstatus(signstatus, userid);
	}

	@Transactional(readOnly = false)
	public void update(WxMeetingSignup wxMeetingSignup) {
		mapper.update(wxMeetingSignup);
		String meetingId = wxMeetingSignup.getMeetingId();
		// 参会人数递增
		logger.info("参会人数递增");
		WxMeeting wm = wxMeetingMapper.get(meetingId);
		if (wm != null) {
			wm.setPersonAttendNum(wm.getPersonAttendNum() + 1);
		}
		wxMeetingMapper.update(wm);
	}
	
//加入群聊
	@Transactional(readOnly=false)
	public void insertGroup(WxMeetingSignup wxMeetingSignup){
		
				String meetingId = wxMeetingSignup.getMeetingId();
				//查一下有无此群
				String userId = wxMeetingSignup.getUserId();
				logger.info("userId=" + userId);
				logger.info("查一下有无此群前" );
				WxGroup wxGroup = wxGroupMapper.findUniqueByProperty("source",meetingId);
				logger.info("查一下有无此群后" );
				if(wxGroup!=null){
					logger.info("wxGroup!=null" );
					WxGroupUser wxGroupUser = new WxGroupUser();
					wxGroupUser.setUserId(userId);
					wxGroupUser.setGroupId(wxGroup.getId());
					try{
		                List<WxGroupUser> wxGroupUserList = wxGroupUserMapper.findList(wxGroupUser);
		                if(wxGroupUserList==null || wxGroupUserList.size()==0){
							logger.info("证明不在群聊中加入群" );
		                    //证明不在群聊中加入群
		                    wxGroupUser.preInsert();
		                    wxGroupUser.setGroupId(wxGroup.getId());
		                    wxGroupUser.setUserId(userId);
		                    wxGroupUser.setIsAdmin("1");
		                    wxGroupUser.setIsRead("0");
		                    wxGroupUser.setIsStopSpeak("0");
		                    wxGroupUser.setJoinTime(new Date());
		                    wxGroupUserMapper.insert(wxGroupUser);
		                }
		            }catch (Exception e){
					    e.printStackTrace();
		            }
				}
	}
	
	
	@Transactional(readOnly = false)
	public List<String> findMeetingByIdAndId(String userid, String meetingid) {
		return wxmeetingsignupmapper.findMeetingByIdAndId(userid, meetingid);
	}

	@Transactional(readOnly = false)
	public List<WxMeetingSignup> findMeetingByIdAndIdtwo(String userid, String meetingid) {
		return wxmeetingsignupmapper.findMeetingByIdAndIdtwo(userid, meetingid);
	}

	public Page<WxMeetingSignup> findSignInList(Page<WxMeetingSignup> page, WxMeetingSignup wxMeetingSignup) {
		return wxmeetingsignupmapper.findSignInList(page, wxMeetingSignup);
	}
	//会议前短信推送用会查看桌号信息
	public List<WxMeetingSignup> notifyMsg(String id) {
		return wxmeetingsignupmapper.notifyMsg(id);
	}
	
	//c查询用户的 最新的一次报名会议
	public List<WxMeetingSignup> findlastmymeeting(String id) {
		return wxmeetingsignupmapper.findlastmymeeting(id);
	}
	
	@Transactional(readOnly = false)
	public void importFileUpdate(WxMeetingSignup wxMeetingSignup) {
		// TODO Auto-generated method stub
		wxmeetingsignupmapper.updateimport(wxMeetingSignup);
	}
	@Transactional(readOnly = false)
	public List<WxMeetingSignup> getmobileandmeetingid(String mobile, String meetingid) {
		// TODO Auto-generated method stub
		return wxmeetingsignupmapper.getmobileandmeetingid(mobile,meetingid);
	}
	@Transactional(readOnly = false)
	public int deletebyuserid(String id) {
		return wxmeetingsignupmapper.deletebyuserid(id);
	}


}