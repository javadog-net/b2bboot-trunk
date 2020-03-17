/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.oa.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.oa.entity.OaNotify;

/**
 * 通知通告MAPPER接口
 * @author jhmis
 * @version 2017-05-16
 */
@MyBatisMapper
public interface OaNotifyMapper extends BaseMapper<OaNotify> {
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
	public Long findCount(OaNotify oaNotify);
	
}