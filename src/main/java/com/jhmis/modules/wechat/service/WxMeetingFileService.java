/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.WxMeetingFile;
import com.jhmis.modules.wechat.mapper.WxMeetingFileMapper;

/**
 * 会议附件上传Service
 * @author tc
 * @version 2019-03-20
 */
@Service
@Transactional(readOnly = true)
public class WxMeetingFileService extends CrudService<WxMeetingFileMapper, WxMeetingFile> {

	public WxMeetingFile get(String id) {
		return super.get(id);
	}
	
	public List<WxMeetingFile> findList(WxMeetingFile wxMeetingFile) {
		return super.findList(wxMeetingFile);
	}
	
	public Page<WxMeetingFile> findPage(Page<WxMeetingFile> page, WxMeetingFile wxMeetingFile) {
		return super.findPage(page, wxMeetingFile);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMeetingFile wxMeetingFile) {
		super.save(wxMeetingFile);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMeetingFile wxMeetingFile) {
		super.delete(wxMeetingFile);
	}
	
}