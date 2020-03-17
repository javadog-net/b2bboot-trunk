/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import javax.xml.ws.spi.WebServiceFeatureAnnotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.WxMeetingDirect;
import com.jhmis.modules.wechat.mapper.WxMeetingDirectMapper;

/**
 * 会议直播表Service
 * @author lvyangzhuo
 * @version 2018-11-26
 */
@Service
@Transactional(readOnly = true)
public class WxMeetingDirectService extends CrudService<WxMeetingDirectMapper, WxMeetingDirect> {
 
	@Autowired
	private WxMeetingDirectMapper wxMeetingDirectMapper;
	
	public WxMeetingDirect get(String id) {
		return super.get(id);
	}
	
	public List<WxMeetingDirect> findList(WxMeetingDirect wxMeetingDirect) {
		return super.findList(wxMeetingDirect);
	}
	
	public Page<WxMeetingDirect> findPage(Page<WxMeetingDirect> page, WxMeetingDirect wxMeetingDirect) {
		return super.findPage(page, wxMeetingDirect);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMeetingDirect wxMeetingDirect) {
		super.save(wxMeetingDirect);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMeetingDirect wxMeetingDirect) {
		super.delete(wxMeetingDirect);
	}
	@Transactional(readOnly = false)
	public List<WxMeetingDirect> findAllList(WxMeetingDirect wxMeetingDirect) {
		return wxMeetingDirectMapper.findAllList(wxMeetingDirect);
	}
	
}