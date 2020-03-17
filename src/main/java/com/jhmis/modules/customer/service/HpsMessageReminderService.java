/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhmis.core.persistence.Page;
import com.jhmis.core.service.CrudService;
import com.jhmis.modules.customer.entity.HpsMessageReminder;
import com.jhmis.modules.customer.mapper.HpsMessageReminderMapper;

/**
 * 消息通知Service
 * @author hdx
 * @version 2020-02-14
 */
@Service
@Transactional(readOnly = true)
public class HpsMessageReminderService extends CrudService<HpsMessageReminderMapper, HpsMessageReminder> {

	@Autowired
	HpsMessageReminderMapper  hpsMessageReminderMapper;
	
	public HpsMessageReminder get(String id) {
		return super.get(id);
	}
	
	public List<HpsMessageReminder> findList(HpsMessageReminder hpsMessageReminder) {
		return super.findList(hpsMessageReminder);
	}
	
	public Page<HpsMessageReminder> findPage(Page<HpsMessageReminder> page, HpsMessageReminder hpsMessageReminder) {
		return super.findPage(page, hpsMessageReminder);
	}
	
	@Transactional(readOnly = false)
	public void save(HpsMessageReminder hpsMessageReminder) {
		super.save(hpsMessageReminder);
	}
	
	@Transactional(readOnly = false)
	public void delete(HpsMessageReminder hpsMessageReminder) {
		super.delete(hpsMessageReminder);
	}
	
	/**
	 * 根据8码获取所有未读消息
	 * @param code88
	 * @return
	 */
	public List<HpsMessageReminder> findMessageByCode88(String code88){
		return hpsMessageReminderMapper.findMessageByCode88(code88);
	}
	
	/**
	 * 根据id，标记消息为已读
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void setIsRead(String id) {
		hpsMessageReminderMapper.setIsRead(id);
	}
	
	/**
	 * 根据88码获取未读消息总条数
	 * @param code88
	 * @return
	 */
	public int findMessageNumber(String code88){
		Integer num = hpsMessageReminderMapper.findMessageNumber(code88);
		if(num != null) {
			return num;
		}else {
			return 0;
		}
	}
	
}