/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxTravel;

/**
 * 行程MAPPER接口
 * @author hdx
 * @version 2019-02-14
 */
@MyBatisMapper
public interface WxTravelMapper extends BaseMapper<WxTravel> {

	int deletebyuserid(String id);
	
}