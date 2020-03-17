/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.sys.entity.GmCity;

/**
 * 工贸城市MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface GmCityMapper extends BaseMapper<GmCity> {

	GmCity findByCityId(String cityId);
	
}