/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.bid.entity.BidInfo;

/**
 * 招投标MAPPER接口
 * @author hdx
 * @version 2019-04-11
 */
@MyBatisMapper
public interface BidInfoMapper extends BaseMapper<BidInfo> {
	
	void updateValid(@Param(value = "id") String id,@Param(value = "valid") String valid );
	List<BidInfo> getVisitProjectList();

}