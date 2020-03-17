/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.wechat.entity.WxMessageRecord;
import com.jhmis.modules.wechat.mapper.WxMessageRecordMapper;

/**
 * 短信履历Service
 * @author hdx
 * @version 2019-02-15
 */
@Service
@Transactional(readOnly = true)
public class WxMessageRecordService extends CrudService<WxMessageRecordMapper, WxMessageRecord> {

	public WxMessageRecord get(String id) {
		return super.get(id);
	}
	
	public List<WxMessageRecord> findList(WxMessageRecord wxMessageRecord) {
		return super.findList(wxMessageRecord);
	}
	
	public Page<WxMessageRecord> findPage(Page<WxMessageRecord> page, WxMessageRecord wxMessageRecord) {
		return super.findPage(page, wxMessageRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(WxMessageRecord wxMessageRecord) {
		super.save(wxMessageRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(WxMessageRecord wxMessageRecord) {
		super.delete(wxMessageRecord);
	}
	
}