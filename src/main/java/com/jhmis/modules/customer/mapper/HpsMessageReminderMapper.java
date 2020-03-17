/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.mapper;

import java.util.List;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.customer.entity.HpsMessageReminder;

/**
 * 消息通知MAPPER接口
 * @author hdx
 * @version 2020-02-14
 */
@MyBatisMapper
public interface HpsMessageReminderMapper extends BaseMapper<HpsMessageReminder> {
	/**
	 * 根据8码获取所有未读消息
	 * @param code88
	 * @return
	 */
	List<HpsMessageReminder> findMessageByCode88(String code88);
	/**
	 * 根据id，标记消息为已读
	 * @param id
	 */
	void setIsRead(String id);
	/**
	 * 根据88码获取未读消息总条数
	 * @param code88
	 * @return
	 */
	Integer findMessageNumber(String code88);
}