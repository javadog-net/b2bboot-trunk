/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.wechat.entity.SkMv;

/**
 * 商空视频MAPPER接口
 * @author Tc
 * @version 2019-05-24
 */
@MyBatisMapper
public interface SkMvMapper extends BaseMapper<SkMv> {

	List<SkMv> findAll();

	List<SkMv> findById(String id);

	List<SkMv> findIsTop();

	void isTop(@Param("id")String id, @Param("top")String top);
	
}