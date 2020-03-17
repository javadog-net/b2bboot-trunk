/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxMeetingSignup;

/**
 * 会议报名表MAPPER接口
 * 
 * @author lvyangzhuo
 * @version 2018-11-26
 */
@MyBatisMapper
public interface WxMeetingSignupMapper extends BaseMapper<WxMeetingSignup> {

	public void updatemeetingstatus(WxMeetingSignup wxMeetingSignup);

	public List<WxMeetingSignup> findmeetingsignupstatus(@Param("signstatus") String signstatus,
			@Param("userid") String userid);

	public List<WxMeetingSignup> findListVo(WxMeetingSignup wxMeetingSignup);

	public List<String> findMeetingByIdAndId(@Param("userid") String userid, @Param("meetingid") String meetingid);

	public Page<WxMeetingSignup> findSignInList(Page<WxMeetingSignup> page, WxMeetingSignup wxMeetingSignup);

	public List<WxMeetingSignup> findMeetingByIdAndIdtwo(@Param("userid") String userid,
			@Param("meetingid") String meetingid);
	//会议前短信推送用会查看桌号信息
	public List<WxMeetingSignup> notifyMsg(@Param("id") String id);

	public void updateimport(WxMeetingSignup wxMeetingSignup);

	public List<WxMeetingSignup> getmobileandmeetingid(@Param("mobile")String mobile, @Param("meetingid")String meetingid);
	
	public List<WxMeetingSignup> findlastmymeeting(@Param("id") String id);

	public int deletebyuserid(String id);

}