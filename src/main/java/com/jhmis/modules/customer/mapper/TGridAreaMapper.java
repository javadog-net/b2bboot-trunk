/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.customer.entity.TGridArea;

/**
 * 网格码表MAPPER接口
 * @author hdx
 * @version 2020-02-25
 */
@MyBatisMapper
public interface TGridAreaMapper extends BaseMapper<TGridArea> {
	
	/**
	 * 获取省份接口
	 * @return
	 */
	public List<String> getProvince();
	
	/**
	 * 根据省名称获取城市
	 * @param province 省份
	 * @return
	 */
	public List<String> getCityByProvince(String province);
	
	/**
	 * 根据省份和城市名称获取区县
	 * @param province 省份
	 * @param city 省份
	 * @return
	 */
	public List<String> getAreaByCity(@Param("province")String province,@Param("city")String city);
	
	
	
	/**
	 * 根据工贸编码，获取工贸名称
	 * @param gmCode
	 * @return
	 */
	String findNameByCode(String gmcode);
}