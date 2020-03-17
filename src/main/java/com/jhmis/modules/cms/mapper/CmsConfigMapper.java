/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.cms.entity.CmsConfig;

/**
 * cms配置管理表MAPPER接口
 * @author lydia
 * @version 2019-08-29
 */
@MyBatisMapper
public interface CmsConfigMapper extends BaseMapper<CmsConfig> {
	
}