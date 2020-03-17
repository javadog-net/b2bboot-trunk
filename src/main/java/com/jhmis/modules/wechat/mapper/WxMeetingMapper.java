/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxMeeting;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 会议表MAPPER接口
 * @author lvyangzhuo
 * @version 2018-11-23
 */
@MyBatisMapper
public interface WxMeetingMapper extends BaseMapper<WxMeeting> {
    List<WxMeeting> findAllMeetingList(WxMeeting wxMeeting);

	Page<WxMeeting> findPage(Page<WxMeeting> page, WxMeeting wxMeeting);

	public void addsignupnum(@Param("meetingid")String meetingid,@Param("num") int num);

	public List<WxMeeting> findlikemeeting(String name);
}