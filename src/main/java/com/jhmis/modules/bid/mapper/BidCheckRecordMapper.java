/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.bid.entity.BidCheckRecord;

/**
 * 招投标MAPPER接口
 * @author hdx
 * @version 2019-04-11
 */
@MyBatisMapper
public interface BidCheckRecordMapper extends BaseMapper<BidCheckRecord> {
	
	

	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public int updateInfo(BidCheckRecord bidCheckRecord);
	
}