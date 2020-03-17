/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.CmsWinPrize;

/**
 * 中奖表MAPPER接口
 * @author tc
 * @version 2019-02-27
 */
@MyBatisMapper
public interface CmsWinPrizeMapper extends BaseMapper<CmsWinPrize> {

	List<CmsWinPrize> findDrawList( @Param("actvid")String actvid,
									@Param("phone")String phone,
									@Param("companyname")String companyname,
									@Param("username")String username
									);

	CmsWinPrize getByid(@Param("actvid")String actvid, @Param("userId")String userId);

	int deletebyuserid(String id);
	
}