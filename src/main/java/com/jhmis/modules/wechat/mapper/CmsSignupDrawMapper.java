/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.CmsSignupDraw;

/**
 * 参与报名抽奖MAPPER接口
 * @author tc
 * @version 2019-02-27
 */
@MyBatisMapper
public interface CmsSignupDrawMapper extends BaseMapper<CmsSignupDraw> {

	int deletebyuserid(String id);

	CmsSignupDraw findReapte(@Param("actvid")String actvid, @Param("userid")String userid);
	
}