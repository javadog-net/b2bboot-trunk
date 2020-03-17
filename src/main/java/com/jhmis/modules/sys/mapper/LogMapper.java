/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.sys.entity.Log;

/**
 * 日志MAPPER接口
 * @author jhmis
 * @version 2017-05-16
 */
@MyBatisMapper
public interface LogMapper extends BaseMapper<Log> {

	public void empty();
}
