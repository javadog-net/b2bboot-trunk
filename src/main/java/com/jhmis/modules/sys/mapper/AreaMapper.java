/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.mapper;

import java.util.List;

import com.jhmis.core.persistence.TreeMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.sys.entity.Area;

/**
 * 区域MAPPER接口
 * @author jhmis
 * @version 2017-05-16
 */
@MyBatisMapper
public interface AreaMapper extends TreeMapper<Area> {

	List<Area> findAllProvice();
	
}
