/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.SkMvLike;

/**
 * 商空点赞MAPPER接口
 * @author tc
 * @version 2019-05-28
 */
@MyBatisMapper
public interface SkMvLikeMapper extends BaseMapper<SkMvLike> {

	SkMvLike getByMidAndUid(@Param("id")String id, @Param("userid")String userid);

	void deleteByMidAndUid(@Param("id")String id, @Param("userid")String userid);

	List<SkMvLike> findOrLike(@Param("id")String id, @Param("userid")String userid);
	
}