/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.mapper;

import com.jhmis.core.persistence.TreeMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.sys.entity.AttCat;

/**
 * 附件管理MAPPER接口
 * @author tity
 * @version 2018-07-06
 */
@MyBatisMapper
public interface AttCatMapper extends TreeMapper<AttCat> {
	
}