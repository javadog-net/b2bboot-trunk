/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.SkMvComment;

/**
 * 评论MAPPER接口
 * @author tc
 * @version 2019-05-28
 */
@MyBatisMapper
public interface SkMvCommentMapper extends BaseMapper<SkMvComment> {

	void updateState(@Param("id")String id, @Param("state")String state,@Param("username")String username);

	List<SkMvComment> findListById(String id);
	
}