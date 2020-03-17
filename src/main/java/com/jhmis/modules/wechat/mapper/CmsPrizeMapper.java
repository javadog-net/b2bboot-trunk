/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.CmsPrize;

/**
 * 奖项表操作MAPPER接口
 * @author tc
 * @version 2019-02-27
 */
@MyBatisMapper
public interface CmsPrizeMapper extends BaseMapper<CmsPrize> {

	void updatePrizeStatus(@Param("prizeId")String prizeId,@Param("tab") String tab);
	
}