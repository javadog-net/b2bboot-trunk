/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.cms.entity.Sensitive;

/**
 * 敏感词管理MAPPER接口
 * @author lydia
 * @version 2019-11-02
 */
@MyBatisMapper
public interface SensitiveMapper extends BaseMapper<Sensitive> {
	
}