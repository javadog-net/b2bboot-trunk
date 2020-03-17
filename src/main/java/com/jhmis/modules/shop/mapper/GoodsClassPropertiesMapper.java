/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.shop.entity.GoodsClassProperties;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性MAPPER接口
 * @author tity
 * @version 2018-07-22
 */
@MyBatisMapper
public interface GoodsClassPropertiesMapper extends BaseMapper<GoodsClassProperties> {
	void deleteByClassId(String classId);

	List<GoodsClassProperties> findConcatProperty (String classId);

	List<GoodsClassProperties> findClassProperties(@Param(value="classId") String classId);
}