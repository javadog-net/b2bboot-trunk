/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.mapper;

import com.jhmis.core.persistence.BaseMapper;
import com.jhmis.core.persistence.annotation.MyBatisMapper;
import com.jhmis.modules.customer.entity.ViewQygProjectdetailviewdate;

/**
 * aMAPPER接口
 * @author a
 * @version 2019-10-29
 */
@MyBatisMapper
public interface ViewQygProjectdetailviewdateMapper extends BaseMapper<ViewQygProjectdetailviewdate> {
	ViewQygProjectdetailviewdate getByProjectcode(String projectcode);
}