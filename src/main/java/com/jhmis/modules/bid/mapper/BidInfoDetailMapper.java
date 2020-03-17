/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.bid.entity.BidInfoDetail;

/**
 * 招投标MAPPER接口
 * @author tc
 * @version 2019-07-23
 */
@MyBatisMapper
public interface BidInfoDetailMapper extends BaseMapper<BidInfoDetail> {

	void updateValid(@Param(value = "id") String id,@Param(value = "valid") String valid );

	List<BidInfoDetail> getTimeProjectList();
	List<BidInfoDetail> getVisitProjectList();
}