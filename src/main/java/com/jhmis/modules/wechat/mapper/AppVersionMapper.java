/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.AppVersion;

/**
 * app版本MAPPER接口
 * @author abc
 * @version 2019-05-10
 */
@MyBatisMapper
public interface AppVersionMapper extends BaseMapper<AppVersion> {
	public AppVersion getNewVersion(@Param("appVersion") AppVersion appVersion,@Param("nowTime") Date nowTime);
}