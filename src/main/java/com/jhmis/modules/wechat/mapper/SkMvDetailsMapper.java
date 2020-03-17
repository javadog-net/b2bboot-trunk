/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.SkMvDetails;

/**
 * 商空视频详情MAPPER接口
 * @author tc
 * @version 2019-05-23
 */
@MyBatisMapper
public interface SkMvDetailsMapper extends BaseMapper<SkMvDetails> {

	void updateVisits(@Param("skMvVisits") String skMvVisits,@Param("id")String id);

	void updateLike(@Param("skMvLike") String skMvLike,@Param("id")String id);

	SkMvDetails getSkMvById(String id);

	void updateComment(@Param("skMvComment") String skMvComment,@Param("id")String id);

	
}