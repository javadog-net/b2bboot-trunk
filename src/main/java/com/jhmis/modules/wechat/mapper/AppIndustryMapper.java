/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.AppIndustry;

/**
 * aMAPPER接口
 * @author abc
 * @version 2019-06-25
 */
@MyBatisMapper
public interface AppIndustryMapper extends BaseMapper<AppIndustry> {
	//根据行业名称获取所属一级分类的id
	public String findOnelevelidByIndustry(@Param("industry") String industry);
}