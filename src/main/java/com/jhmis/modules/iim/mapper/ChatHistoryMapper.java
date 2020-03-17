/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.iim.mapper;

import java.util.List;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.iim.entity.ChatHistory;

/**
 * 聊天记录MAPPER接口
 * @author jhmis
 * @version 2015-12-29
 */
@MyBatisMapper
public interface ChatHistoryMapper extends BaseMapper<ChatHistory> {
	
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<ChatHistory> findLogList(ChatHistory entity);
	

	/**
	 * 查询群组列表数据
	 * @param entity
	 * @return
	 */
	public List<ChatHistory> findGroupLogList(ChatHistory entity);
	
	public int findUnReadCount(ChatHistory chatHistory);
	
}