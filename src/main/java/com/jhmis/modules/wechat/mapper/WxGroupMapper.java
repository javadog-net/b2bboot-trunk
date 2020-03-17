/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.WxGroup;

/**
 * 群组表MAPPER接口
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@MyBatisMapper
public interface WxGroupMapper extends BaseMapper<WxGroup> {

public 	List<WxGroup> findrepeatgroup(@Param ("userid")String userid, @Param("groupname")String groupname);

public WxGroup findbysource(String source);
	
}