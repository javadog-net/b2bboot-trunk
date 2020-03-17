/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.tools.entity.CmsShortMsg;

/**
 * 发送短信MAPPER接口
 * @author tc
 * @version 2019-09-04
 */
@MyBatisMapper
public interface CmsShortMsgMapper extends BaseMapper<CmsShortMsg> {
	
}