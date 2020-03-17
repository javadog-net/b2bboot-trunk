/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.mapper.dispatcher;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.process.entity.dispatcher.CmsOperlogs;

/**
 * 抢派单操作日志MAPPER接口
 * @author tc
 * @version 2019-09-19
 */
@MyBatisMapper
public interface CmsOperlogsMapper extends BaseMapper<CmsOperlogs> {
	
}