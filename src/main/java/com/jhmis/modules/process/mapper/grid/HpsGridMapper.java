/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.mapper.grid;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.process.entity.grid.HpsGrid;

/**
 * 省市区匹配工贸MAPPER接口
 * @author mll
 * @version 2019-09-25
 */
@MyBatisMapper
public interface HpsGridMapper extends BaseMapper<HpsGrid> {
	
	/**
	 * 根据工贸id获取工贸名称
	 * @param centerCode
	 * @return
	 */
	public String findByCenterCode(String centerCode);
	
	/**
	 * 根据属中心(工贸)名称获取下属所有城市 
	 */
	public List<String> findCityByCenter(@Param("centerName") String centerName);
}