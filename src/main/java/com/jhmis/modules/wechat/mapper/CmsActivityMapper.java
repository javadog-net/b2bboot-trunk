/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.CmsActivity;

/**
 * 活动表操作MAPPER接口
 * @author tc
 * @version 2019-02-27
 */
@MyBatisMapper
public interface CmsActivityMapper extends BaseMapper<CmsActivity> {

	void updateActivityStatus(@Param("actvid")String actvId, @Param("tab")String tab);
	
}