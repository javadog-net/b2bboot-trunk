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
import com.jhmis.modules.wechat.entity.WxMeetingSchedule;
import com.jhmis.modules.wechat.mapper.WxMeetingScheduleMapper;

/**
 * 会议日程表Service
 * @author lvyangzhuo
 * @version 2018-11-23
 */
@Service
@Transactional(readOnly = true)
public class WxMeetingScheduleService extends CrudService<WxMeetingScheduleMapper, WxMeetingSchedule> {

	@Autowired
	private WxMeetingScheduleMapper wxMeetingScheduleMapper;

	public WxMeetingSchedule get(String id) {
		return super.get(id);
	}
	
	public List<WxMeetingSchedule> findList(WxMeetingSchedule wxMeetingSchedule) {
		return super.findList(wxMeetingSchedule);
	}
	
	public Page<WxMeetingSchedule> findPage(Page<WxMeetingSchedule> page, WxMeetingSchedule wxMeetingSchedule) {
		return super.findPage(page, wxMeetingSchedule);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMeetingSchedule wxMeetingSchedule) {
		super.save(wxMeetingSchedule);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMeetingSchedule wxMeetingSchedule) {
		super.delete(wxMeetingSchedule);
	}

	public List<WxMeetingSchedule> findMeetingScheduleList(WxMeetingSchedule wxMeetingSchedule) {
		
		
		return wxMeetingScheduleMapper.findMeetingScheduleList(wxMeetingSchedule);
	}
	
}