/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.iim.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.iim.entity.MailBox;

/**
 * 发件箱MAPPER接口
 * @author jhmis
 * @version 2015-11-15
 */
@MyBatisMapper
public interface MailBoxMapper extends BaseMapper<MailBox> {
	
	public Integer getCount(MailBox entity);
	
}