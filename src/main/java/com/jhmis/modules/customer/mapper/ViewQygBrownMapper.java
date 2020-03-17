/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.customer.entity.ViewQygBrown;

import java.util.List;

/**
 * 工程版信息视图MAPPER接口
 * @author hdx
 * @version 2019-05-29
 */
@MyBatisMapper
public interface ViewQygBrownMapper extends BaseMapper<ViewQygBrown> {
	public List<ViewQygBrown> findByMine(ViewQygBrown viewQygBrown);
}